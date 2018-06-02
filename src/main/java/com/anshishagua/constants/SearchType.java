package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/6/1
 * Time: 下午5:23
 */

public enum SearchType {
    TABLE("TABLE", "表搜索"),
    TAG("TAG", "标签搜索"),
    INDEX("INDEX", "指标搜索"),
    ALL("ALL", "搜索全部");

    private String value;
    private String description;

    SearchType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}