package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/5/31
 * Time: 下午4:30
 */

public enum TableType {
    BASIC("BASIC", "基础表"),
    DERIVED("DERIVED", "加工表"),
    UNKNOWN("UNKNOWN", "未知类型");

    private String value;
    private String description;

    TableType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static TableType parseByValue(String value) {
        for (TableType tableType : values()) {
            if (tableType.value.equals(value)) {
                return tableType;
            }
        }

        return UNKNOWN;
    }
}