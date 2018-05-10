package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/8
 * Time: 下午4:52
 */

public class Insert extends AbstractNode<Void> {
    private String tableName;
    private Query query;
    private boolean overwrite;

    public Insert() {
    }

    public Insert(String tableName, Query query, boolean overwrite) {
        Objects.requireNonNull(tableName);
        Objects.requireNonNull(query);

        this.tableName = tableName;
        this.query = query;
        this.overwrite = overwrite;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        Objects.requireNonNull(tableName);

        this.tableName = tableName;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        Objects.requireNonNull(query);

        this.query = query;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    @Override
    public String toString() {
        return String.format("INSERT %s TABLE `%s` %s", overwrite ? "OVERWRITE" : "INTO", tableName, query);
    }
}