package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:22
 */

public class IsNotNull extends AbstractNode<Void> implements CompareNode {
    public IsNotNull(Node child) {
        super(child);
    }

    @Override
    public String toString() {
        return String.format("%s IS NOT NULL", getChild(0));
    }

    @Override
    public Node negate() {
        return new IsNull(getChild(0));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IsNotNull && Objects.equals(this.getChild(0), ((Equal) obj).getChild(0));
    }
}