package com.anshishagua.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:41
 */

public class StringUtils {
    public static String singleQuote(String string) {
        return String.format("'%s'", string);
    }

    public static String quote(String string, String ch) {
        return String.format("%s%s%s", ch, string, ch);
    }

    public static String backQuote(String string) {
        return String.format("`%s`", string);
    }

    public static class Person {
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Person)) {
                return false;
            }

            return this.age == ((Person) obj).age && Objects.equals(this.name, ((Person) obj).name);
        }
    }

    public static void main(String [] args) {
        List<String> sqls = new ArrayList<>();

        sqls.add("a");
        sqls.add("b");
        sqls.add("c");

        String value = JSON.toJSONString(sqls);

        System.out.println(value);

        List<String> a = JSON.parseArray(value, String.class);

        System.out.println(a);

        Map<String, String> map = new HashMap<>();

        String prev = map.put("a", "a");

        System.out.println(prev);

        prev = map.put("a", "b");

        System.out.println(prev);
    }
}