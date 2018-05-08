package com.anshishagua.parser.nodes.bool;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/5/5
 * Time: 下午3:37
 */

public class NotIn extends AbstractNode<Void> implements Bool {
    public NotIn(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s NOT IN (%s)", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new In(getChild(0), getChild(1));
    }
}