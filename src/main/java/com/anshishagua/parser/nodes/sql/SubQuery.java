package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午5:06
 */

public class SubQuery extends AbstractNode<Void> {
    private Query query;
    private String alias;

    public SubQuery(Query query, String alias) {
        Objects.requireNonNull(query);
        Objects.requireNonNull(alias);

        this.query = query;
        this.alias = alias;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", query, alias);
    }
}