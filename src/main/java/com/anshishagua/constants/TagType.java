package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/6/11
 * Time: 下午2:21
 */

public enum TagType {
    STANDARD("STANDARD", "标准标签"),
    USER_DEFINED("USER_DEFINED", "自定义标签"),
    UNKNOWN("", "未知类型");

    private String value;
    private String description;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    TagType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static TagType parseByValue(String value) {
        for (TagType tagType : values()) {
            if (tagType.value.equals(value)) {
                return tagType;
            }
        }

        return UNKNOWN;
    }
}