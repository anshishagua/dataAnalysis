package com.anshishagua.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/7/6
 * Time: 下午3:03
 */

public class SparkStreamJob {
    static class JavaSparkSessionSingleton {
        private static transient SparkSession instance = null;

        public static SparkSession getInstance(SparkConf sparkConf) {
            if (instance == null) {
                instance = SparkSession
                        .builder()
                        .config(sparkConf)
                        .getOrCreate();
            }

            return instance;
        }
    }

    public static void main(String [] args) {
        if (args.length < 1 || StringUtils.isBlank(args[0])) {
            System.exit(-1);
        }

        JSONObject json = JSON.parseObject(args[0]);

        KafkaDataSource kafkaDataSource = JSON.parseObject(json.getString("kafkaDataSource"), KafkaDataSource.class);
        TableInfo tableInfo = JSON.parseObject(json.getString("tableInfo"), TableInfo.class);
        List<String> sqls = JSON.parseArray(json.getString("sqls"), String.class);
        String appName = json.getString("appName");

        SparkConf sparkConf = new SparkConf().setAppName(appName);
        JavaStreamingContext context = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", kafkaDataSource.getBootstrapServers());
        kafkaParams.put("key.deserializer", kafkaDataSource.getKeyDeserializer());
        kafkaParams.put("key.serializer", kafkaDataSource.getKeySerializer());
        kafkaParams.put("value.serializer", kafkaDataSource.getValueSerializer());
        kafkaParams.put("value.deserializer", kafkaDataSource.getValueDeserializer());
        kafkaParams.put("group.id", kafkaDataSource.getConsumerGroup());
        kafkaParams.put("auto.offset.reset", kafkaDataSource.getAutoOffsetReset());
        kafkaParams.put("enable.auto.commit", kafkaDataSource.isAutoCommit());

        String sourceTopic = kafkaDataSource.getSourceTopic();
        String destTopic = kafkaDataSource.getDestTopic();
        List<String> topics = Arrays.asList(sourceTopic);

        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(context, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        JavaDStream<String> dStream = stream.map(consumerRecord -> consumerRecord.value());

        List<StructField> fields = new ArrayList<>();

        List<TableField> tableFields = tableInfo.getFields();

        tableFields.forEach(tableField -> {
            DataType dataType = null;

            String type = tableField.getType();

            switch (type) {
                case "TINYINT":
                case "SMALLINT":
                    dataType = DataTypes.ShortType;
                    break;
                case "INT":
                    dataType = DataTypes.IntegerType;
                    break;
                case "BIGINT":
                    dataType = DataTypes.LongType;
                    break;
                case "BOOLEAN":
                    dataType = DataTypes.BooleanType;
                    break;
                case "FLOAT":
                    dataType = DataTypes.FloatType;
                    break;
                case "DOUBLE":
                    dataType = DataTypes.DoubleType;
                    break;
                case "DATE":
                    dataType = DataTypes.DateType;
                    break;
                case "TIMESTAMP":
                    dataType = DataTypes.TimestampType;
                    break;
                default:
                    dataType = DataTypes.StringType;
                    break;
            }

            fields.add(DataTypes.createStructField(tableField.getName(), dataType, true));
        });

        StructType schema = DataTypes.createStructType(fields);

        dStream.foreachRDD((rdd, time) -> {
            SparkSession spark = JavaSparkSessionSingleton.getInstance(rdd.context().getConf());

            JavaRDD<Row> rowRDD = rdd.map((Function<String, Row>) jsonString -> {
                JSONObject jsonObject = JSON.parseObject(jsonString);

                Object [] values = new Object[fields.size()];

                for (int i = 0; i < values.length; ++i) {
                    TableField tableField = tableFields.get(i);
                    Object object = jsonObject.get(tableField.getName());
                    values[i] = object;
                    ++i;
                }

                return RowFactory.create(values);
            });

            Dataset<Row> dataset = spark.createDataFrame(rowRDD, schema);

            dataset.createOrReplaceTempView(tableInfo.getName());

            for (String sql : sqls) {
                Dataset<Row> result = spark.sql(sql);

                JavaRDD<String> stringJavaRDD = result.toJavaRDD().map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("source", row.getString(0));
                    map.put("tag", row.getString(1));

                    return JSON.toJSONString(map);
                });

                stringJavaRDD.foreachPartition(iterator -> {
                    KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaParams);

                    while (iterator.hasNext()) {
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(destTopic, iterator.next());
                        producer.send(producerRecord);
                    }
                });
            }
        });

        context.start();

        try {
            context.awaitTermination();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}