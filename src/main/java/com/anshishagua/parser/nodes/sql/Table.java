package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午4:59
 */

public class Table extends AbstractNode<Void> {
    private String tableName;
    private String alias;

    public Table(String tableName) {
        Objects.requireNonNull(tableName);

        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public Table(String tableName, String alias) {
        Objects.requireNonNull(tableName);
        Objects.requireNonNull(alias);

        this.tableName = tableName;
        this.alias = alias;
    }

    @Override
    public String toString() {
        if (alias != null) {
            return String.format("`%s` AS `%s`", tableName, alias);
        }

        return String.format("`%s`", tableName);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Table && Objects.equals(this.tableName, ((Table) obj).tableName);
    }
}