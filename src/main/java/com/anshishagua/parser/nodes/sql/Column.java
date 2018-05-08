package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.utils.StringUtils;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:48
 */

public class Column extends AbstractNode<Void> {
    private String tableName;
    private String columnName;
    private String alias;

    public Column(String tableName, String columnName, String alias) {
        Objects.requireNonNull(tableName);
        Objects.requireNonNull(columnName);
        Objects.requireNonNull(alias);

        this.tableName = tableName;
        this.columnName = columnName;
        this.alias = alias;
    }

    public Column(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        if (alias != null) {
            return String.format("%s.%s AS %s", StringUtils.quote(tableName, "`"), StringUtils.quote(columnName, "`"), StringUtils.quote(alias, "`"));
        }

        return String.format("%s.%s", StringUtils.quote(tableName, "`"), StringUtils.quote(columnName, "`"));
    }
}