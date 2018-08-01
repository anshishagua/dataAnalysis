package com.anshishagua.service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;

/**
 * User: lixiao
 * Date: 2018/6/2
 * Time: 下午10:24
 */

public class SparkService {
    private static class Student implements Serializable {
        private long id;
        private String name;
        private int age;
        private String sex;
        private long classId;

        public Student() {

        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public long getClassId() {
            return classId;
        }

        public void setClassId(long classId) {
            this.classId = classId;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    ", classId=" + classId +
                    '}';
        }
    }

    private static class Class implements Serializable {
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Class{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String [] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("app");

        JavaSparkContext context = new JavaSparkContext(sparkConf);

        JavaRDD<String> rdd = context.textFile("/Users/lixiao/code/dataAnalysis/src/main/resources/samples/student.txt");
        JavaRDD<Student> student = rdd.map(new Function<String, Student>() {
            @Override
            public Student call(String string) throws Exception {
                String [] fields = string.split(",");

                Student student = new Student();
                student.setId(Long.parseLong(fields[0]));
                student.setName(fields[1]);
                student.setAge(Integer.parseInt(fields[2]));
                student.setSex(fields[3]);
                student.setClassId(Long.parseLong(fields[4]));

                return student;
            }
        });

        System.out.println(student.collect());

        rdd = context.textFile("/Users/lixiao/code/dataAnalysis/src/main/resources/samples/class.txt");
        JavaRDD<Class> classes = rdd.map(new Function<String, Class>() {
            @Override
            public Class call(String value) throws Exception {
                Class result = new Class();

                result.setId(Long.parseLong(value.split(",")[0]));
                result.setName(value.split(",")[1]);

                return result;
            }
        });

        System.out.println(classes.collect());

        JavaPairRDD<Long, Student> studentJavaPairRDD = student.mapToPair(new PairFunction<Student, Long, Student>() {
            @Override
            public Tuple2<Long, Student> call(Student student) throws Exception {
                return new Tuple2<>(student.id, student);
            }
        });

        JavaPairRDD<Long, Class> classJavaPairRDD = classes.mapToPair(new PairFunction<Class, Long, Class>() {
            @Override
            public Tuple2<Long, Class> call(Class aClass) throws Exception {
                return new Tuple2<>(aClass.id, aClass);
            }
        });

        studentJavaPairRDD.join(null);
    }
}
