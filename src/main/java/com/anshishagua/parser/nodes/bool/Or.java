package com.anshishagua.parser.nodes.bool;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:25
 */

public class Or extends AbstractNode<Void> implements Bool {
    public Or(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s OR %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new And(getChild(0).negate(), getChild(1).negate());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Or
                && Objects.equals(this.getChild(0), ((Or) obj).getChild(0))
                && Objects.equals(this.getChild(1), ((Or) obj).getChild(1));
    }
}