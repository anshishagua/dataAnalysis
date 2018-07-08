package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/7/5
 * Time: 下午4:31
 */

public enum DataType {
    BATCH("BATCH", "批量数据"),
    STREAM("STREAM", "流式数据"),
    UNKNOWN("", "未知类型");

    private String value;
    private String description;

    public String getValue() {
        return value;
    }

    DataType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static DataType parseByValue(String value) {
        for (DataType dataType : values()) {
            if (dataType.value.equals(value)) {
                return dataType;
            }
        }

        return UNKNOWN;
    }
}