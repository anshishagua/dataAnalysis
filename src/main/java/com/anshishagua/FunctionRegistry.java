package com.anshishagua;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.aggregation.Avg;
import com.anshishagua.parser.nodes.function.aggregation.Count;
import com.anshishagua.parser.nodes.function.Sin;
import com.anshishagua.parser.nodes.function.aggregation.Sum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午4:06
 */

public class FunctionRegistry {
    private static Map<String, Integer> argumentsMap = new HashMap<>();

    static {
        argumentsMap.put("sin", 1);
        argumentsMap.put("length", 1);
        argumentsMap.put("current_date", 0);
        argumentsMap.put("sum", 1);
        argumentsMap.put("avg", 1);
        argumentsMap.put("count", 1);
    }

    public static boolean contains(String functionName) {
        if (functionName == null) {
            return false;
        }

        return argumentsMap.containsKey(functionName);
    }

    public static int get(String functionName) {
        if (functionName == null) {
            return -1;
        }

        return argumentsMap.get(functionName);
    }

    public static Node newNode(String name, List<Node> children) {
        switch (name) {
            case "sum":
                return new Sum(children);
            case "avg":
                return new Avg(children);
            case "sin":
                return new Sin(children);
            case "count":
                return new Count(children);
            default:
                throw new RuntimeException("Not supported function:" + name);
        }
    }
}