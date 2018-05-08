package com.anshishagua.parser.nodes.sql;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午5:02
 */

public enum JoinType {
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    INNER_JOIN("INNER JOIN");

    private String sql;

    JoinType(String sql) {
        this.sql = sql;
    }

    public String toSQL() {
        return sql;
    }

    public static JoinType parse(String string) {
        for (JoinType joinType : values()) {
            if (joinType.sql.equalsIgnoreCase(string)) {
                return joinType;
            }
        }

        return null;
    }
}