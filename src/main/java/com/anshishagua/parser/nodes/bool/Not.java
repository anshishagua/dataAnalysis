package com.anshishagua.parser.nodes.bool;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.sql.Condition;

import java.util.Arrays;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:07
 */

public class Not extends AbstractNode<Void> implements Bool {
    public Not(Node child) {
        super(Arrays.asList(new Node[] {child}));
    }

    @Override
    public String toString() {
        return String.format("NOT %s", getChild(0));
    }

    @Override
    public Node negate() {
        return getChild(0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Not && Objects.equals(this.getChild(0), ((Not) obj).getChild(0));
    }
}