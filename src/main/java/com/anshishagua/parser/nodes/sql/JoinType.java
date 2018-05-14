package com.anshishagua.parser.nodes.sql;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午5:02
 */

public enum JoinType {
    LEFT_JOIN("LEFT JOIN", "左连接"),
    RIGHT_JOIN("RIGHT JOIN", "右连接"),
    INNER_JOIN("INNER JOIN", "内连接");

    private String value;
    private String description;

    JoinType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String toSQL() {
        return value;
    }

    public static JoinType parse(String string) {
        for (JoinType joinType : values()) {
            if (joinType.value.equalsIgnoreCase(string)) {
                return joinType;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}