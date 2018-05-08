package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

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
}