package com.anshishagua.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * User: lixiao
 * Date: 2018/6/4
 * Time: 下午4:21
 */

public class SparkStreamingUtils {
    public static void main(String [] args) {
        SparkConf conf = new SparkConf()
                .setMaster("local[5]")
                .setAppName("NetworkWordCount");
        JavaStreamingContext context = new JavaStreamingContext(conf, Durations.seconds(1));

        JavaReceiverInputDStream<String> lines = context.socketTextStream("localhost", 9999);

        JavaDStream<String> words = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

        JavaPairDStream<String, Long> pairs = words.mapToPair(s -> new Tuple2<>(s, 1L));
        JavaPairDStream<String, Long> counts = pairs.reduceByKey((a, b) -> a + b);

        counts.reduceByKeyAndWindow((i, j) -> i + j, Durations.seconds(10), Durations.seconds(5));

        counts.print();

        counts.foreachRDD(stringLongJavaPairRDD -> {
            
        });

        JavaRDD javaRDD;

        context.start();

        try {
            context.awaitTermination();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
