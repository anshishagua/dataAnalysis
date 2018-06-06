package com.anshishagua.parser;

import com.anshishagua.object.DataType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:08
 */

public enum BasicType {
    Boolean(0, "布尔型"),
    Short(10, "短整型"),
    Integer(20, "整形"),
    Long(30, "长整形"),
    Float(40, "浮点型"),
    Double(50, "双精度浮点型"),
    String(60, "字符串型"),
    Date(70, "日期型"),
    Timestamp(80, "时间戳型"),
    Null(90);

    private int priority;
    private String description;

    BasicType(int priority, String description) {
        this.priority = priority;
        this.description = description;
    }

    BasicType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public static final Set<BasicType> NUMERIC_TYPES = new HashSet<>(Arrays.asList(new BasicType[] {Short, Integer, Long, Float, Double}));

    public static final Set<BasicType> DECIMAL_TYPES = new HashSet<>(Arrays.asList(Float, Double));

    public boolean isNumeric() {
        return NUMERIC_TYPES.contains(this);
    }

    public boolean isDecimal() {
        return DECIMAL_TYPES.contains(this);
    }

    public boolean isBoolean() {
        return this == Boolean;
    }

    public boolean isString() {
        return this == String;
    }

    public boolean isDate() {
        return this == Date;
    }

    public boolean isTimestamp() {
        return this == Timestamp;
    }

    public static boolean theSameBasicType(BasicType a, BasicType b) {
        return (a.isNumeric() && b.isNumeric())
                || (a.isBoolean() && b.isBoolean())
                || (a.isDate() && b.isDate())
                || (a.isTimestamp() && b.isTimestamp())
                || (a.isDate() && b.isDate())
                || (a.isString() && b.isString());
    }

    public DataType toDataType() {
        DataType dataType = new DataType();

        switch (this) {
            case Boolean:
                dataType.setValue("BOOLEAN");

                return dataType;
            case Integer:
                dataType.setValue("INT");

                return dataType;
            case Long:
                dataType.setValue("BIGINT");

                return dataType;
            case Float:
                dataType.setValue("FLOAT");

                return dataType;
            case Double:
                dataType.setValue("DOUBLE");

                return dataType;
            default:
                dataType.setValue("STRING");

                return dataType;
        }
    }
}