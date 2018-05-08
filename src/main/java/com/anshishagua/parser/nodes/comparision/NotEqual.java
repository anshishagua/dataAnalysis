package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:21
 */

public class NotEqual extends AbstractNode<Void> implements CompareNode {
    public NotEqual(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s != %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new Equal(getChild(0), getChild(1));
    }
}