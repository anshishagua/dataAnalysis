package com.anshishagua.parser.nodes.sql;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/28
 * Time: 下午4:51
 */

public class Alias extends AbstractNode<Void> {
    private String aliasName;

    public Alias(Node child, String aliasName) {
        Objects.requireNonNull(aliasName);
        Objects.requireNonNull(child);

        setChildren(child);

        this.aliasName = aliasName;
    }

    @Override
    public String toString() {
        return String.format("%s AS %s", getChild(0).toString(), aliasName);
    }
}