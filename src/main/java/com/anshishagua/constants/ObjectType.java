package com.anshishagua.constants;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/6/6
 * Time: 下午2:51
 */

public enum ObjectType {
    SYSTEM_PARAM("SYSTEM_PARAM", "系统参数"),
    TABLE("TABLE", "表"),
    TAG("TAG", "标签"),
    BASIC_INDEX("BASIC_INDEX", "基础指标"),
    DERIVED_INDEX("DERIVED_INDEX", "派生指标"),
    UNKNOWN("", "");

    private String value;
    private String description;

    ObjectType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ObjectType parseByValue(String value) {
        for (ObjectType objectType : values()) {
            if (objectType.value.equals(value)) {
                return objectType;
            }
        }

        return UNKNOWN;
    }
}