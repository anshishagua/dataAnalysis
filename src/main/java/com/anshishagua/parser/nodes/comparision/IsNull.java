package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:22
 */

public class IsNull extends AbstractNode<Void> implements CompareNode {
    public IsNull(Node child) {
        super(child);
    }

    @Override
    public String toString() {
        return String.format("%s IS NULL", getChild(0));
    }

    @Override
    public Node negate() {
        return new IsNotNull(getChild(0));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IsNull && Objects.equals(this.getChild(0), ((Equal) obj).getChild(0));
    }
}