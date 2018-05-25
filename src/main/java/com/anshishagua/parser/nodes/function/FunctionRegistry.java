package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.aggregation.Avg;
import com.anshishagua.parser.nodes.function.aggregation.Count;
import com.anshishagua.parser.nodes.function.aggregation.Max;
import com.anshishagua.parser.nodes.function.aggregation.Min;
import com.anshishagua.parser.nodes.function.condition.If;
import com.anshishagua.parser.nodes.function.conversion.ToBoolean;
import com.anshishagua.parser.nodes.function.conversion.ToDate;
import com.anshishagua.parser.nodes.function.conversion.ToDouble;
import com.anshishagua.parser.nodes.function.conversion.ToInteger;
import com.anshishagua.parser.nodes.function.conversion.ToLong;
import com.anshishagua.parser.nodes.function.conversion.ToString;
import com.anshishagua.parser.nodes.function.date.CurrentDate;
import com.anshishagua.parser.nodes.function.date.CurrentTimestamp;
import com.anshishagua.parser.nodes.function.date.DateAdd;
import com.anshishagua.parser.nodes.function.date.DateDiff;
import com.anshishagua.parser.nodes.function.date.Day;
import com.anshishagua.parser.nodes.function.date.Hour;
import com.anshishagua.parser.nodes.function.date.Minute;
import com.anshishagua.parser.nodes.function.date.Month;
import com.anshishagua.parser.nodes.function.date.Second;
import com.anshishagua.parser.nodes.function.date.Week;
import com.anshishagua.parser.nodes.function.date.Year;
import com.anshishagua.parser.nodes.function.numeric.Sin;
import com.anshishagua.parser.nodes.function.aggregation.Sum;
import com.anshishagua.parser.nodes.function.string.Concat;
import com.anshishagua.parser.nodes.function.string.Length;
import com.anshishagua.parser.nodes.function.string.LowerCase;
import com.anshishagua.parser.nodes.function.string.Replace;
import com.anshishagua.parser.nodes.function.string.Trim;
import com.anshishagua.parser.nodes.function.string.UpperCase;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午4:06
 */

public class FunctionRegistry {
    private static Map<String, Class<?>> CLASS_MAP = new HashMap<>();

    private static void registerClass() {
        CLASS_MAP.put(Sum.class.getSimpleName().toLowerCase(), Sum.class);
        CLASS_MAP.put(Avg.class.getSimpleName().toLowerCase(), Avg.class);
        CLASS_MAP.put(Count.class.getSimpleName().toLowerCase(), Count.class);
        CLASS_MAP.put(Max.class.getSimpleName().toLowerCase(), Max.class);
        CLASS_MAP.put(Min.class.getSimpleName().toLowerCase(), Min.class);
        CLASS_MAP.put(Sin.class.getSimpleName().toLowerCase(), Sin.class);
        CLASS_MAP.put(Concat.class.getSimpleName().toLowerCase(), Concat.class);
        CLASS_MAP.put(Length.class.getSimpleName().toLowerCase(), Length.class);
        CLASS_MAP.put("lower_case", LowerCase.class);
        CLASS_MAP.put("upper_case", UpperCase.class);
        CLASS_MAP.put(Replace.class.getSimpleName().toLowerCase(), Replace.class);
        CLASS_MAP.put(Trim.class.getSimpleName().toLowerCase(), Trim.class);
        CLASS_MAP.put(If.class.getSimpleName().toLowerCase(), If.class);

        CLASS_MAP.put("current_date", CurrentDate.class);
        CLASS_MAP.put("current_timestamp", CurrentTimestamp.class);
        CLASS_MAP.put("date_add", DateAdd.class);
        CLASS_MAP.put("date_diff", DateDiff.class);
        CLASS_MAP.put("day", Day.class);
        CLASS_MAP.put("hour", Hour.class);
        CLASS_MAP.put("minute", Minute.class);
        CLASS_MAP.put("month", Month.class);
        CLASS_MAP.put("second", Second.class);
        CLASS_MAP.put("week", Week.class);
        CLASS_MAP.put("year", Year.class);

        CLASS_MAP.put("to_string", ToString.class);
        CLASS_MAP.put("to_double", ToDouble.class);
        CLASS_MAP.put("to_date", ToDate.class);
        CLASS_MAP.put("to_boolean", ToBoolean.class);
        CLASS_MAP.put("to_integer", ToInteger.class);
        CLASS_MAP.put("to_long", ToLong.class);
    }

    static {
        registerClass();
    }

    public static boolean contains(String functionName) {
        Objects.requireNonNull(functionName);

        return CLASS_MAP.containsKey(functionName);
    }

    public static Node createNode(String name, List<Node> children) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(children);

        List<Node> list = new ArrayList<>(children.size());

        for (Node node : list) {
            if (node instanceof FunctionNode) {
                list.add(createNode(((FunctionNode) node).getName(), node.getChildren()));
            } else {
                list.add(node);
            }
        }

        Class<?> nodeClass = CLASS_MAP.get(name);

        if (nodeClass == null) {
            throw new RuntimeException("Not supported function:" + name);
        }

        Constructor<Node> constructor = null;

        try {
            constructor = (Constructor<Node>) nodeClass.getConstructor(List.class);
            return constructor.newInstance(children);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}