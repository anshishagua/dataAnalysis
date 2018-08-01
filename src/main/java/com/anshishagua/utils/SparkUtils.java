package com.anshishagua.utils;

import org.apache.hive.jdbc.HiveStatement;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * User: lixiao
 * Date: 2018/6/3
 * Time: 上午11:35
 */

public class SparkUtils {
    public static void main(String [] args) {
        SparkConf sparkConf = new SparkConf().setMaster("spark://192.168.105.81:2277").setAppName("aaaa");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();

        sparkSession.sql("select 1");
        JavaRDD<String> rdd = sparkContext.parallelize(Arrays.asList("a", "b", "c"));

        rdd = rdd.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String v1) throws Exception {
                return v1.compareTo("a") <= 0;
            }
        });

        System.out.println(rdd.collect());
        System.out.println("app_id:" + sparkContext.sc().applicationId());
    }

    public static void main2(String [] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("aaa");

        JavaSparkContext context = new JavaSparkContext(sparkConf);

        JavaRDD<String> a = context.parallelize(Arrays.asList("a", "b", "c"));
        JavaRDD<String> b = context.parallelize(Arrays.asList("c", "d", "e"));

        System.out.println(a.union(b).distinct().collect());
        System.out.println(a.intersection(b).collect());
        System.out.println(a.subtract(b).collect());
        System.out.println(a.cartesian(b).collect());

        System.out.println(context.parallelize(Arrays.asList(1, 3, 8, 9)).fold(0, (x, y) -> x + y));
        //System.out.println(context.parallelize(Arrays.asList(111, 3, 4, 1)).aggregate());

        System.out.println(context.parallelize(Arrays.asList("aaa", "bb", "c")).filter(s -> s.length() > 2).collect());

        System.out.println(context.parallelize(Arrays.asList(1, 2, 3, 4)).map(it -> it * it).filter(it -> it > 10).collect());

        a = context.textFile("/Users/lixiao/code/dataAnalysis/src/main/resources/samples/class.txt");
        JavaPairRDD<Long, List<String>> classRDD = a.mapToPair(s -> {
            String [] fields = s.split(",");

            return new Tuple2<>(Long.parseLong(fields[0]), Arrays.asList(fields));
        });

        b = context.textFile("/Users/lixiao/code/dataAnalysis/src/main/resources/samples/student.txt");
        JavaPairRDD<Long, List<String>> studentRDD = b.mapToPair(s -> {
            String [] fields = s.split(",");

            return new Tuple2<>(Long.parseLong(fields[4]), Arrays.asList(fields));
        });

        JavaPairRDD<Long, Tuple2<List<String>, List<String>>> result = classRDD.join(studentRDD);

        System.out.println(result.values().collect());
        System.out.println(result.countByKey());
        System.out.println(result.sortByKey().collect());

        JavaPairRDD<String, String> pairRDD = context.parallelize(Arrays.asList("1,1", "43,3", "5,5")).mapToPair(s -> new Tuple2<>(s.split(",")[0], s.split(",")[1]));

        System.out.println(pairRDD.sortByKey().collect());

        Deque deque;

    }
}
