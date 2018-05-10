package com.anshishagua.parser.nodes.bool;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:24
 */

public class And extends AbstractNode<Void> implements Bool {
    public And(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s AND %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new And(getChild(0).negate(), getChild(1).negate());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof And
                && Objects.equals(this.getChild(0), ((And) obj).getChild(0))
                && Objects.equals(this.getChild(1), ((And) obj).getChild(1));
    }
}