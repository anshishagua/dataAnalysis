package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 上午10:58
 */

public enum IndexType {
    BASIC("BASIC", "基础指标"),
    DERIVED("DERIVED", "派生指标"),
    UNKNOWN("UNKNOWN", "未知类型");

    private String value;
    private String description;

    IndexType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static IndexType parseByValue(String value) {
        for (IndexType indexType : values()) {
            if (indexType.value.equals(value)) {
                return indexType;
            }
        }

        return UNKNOWN;
    }
}