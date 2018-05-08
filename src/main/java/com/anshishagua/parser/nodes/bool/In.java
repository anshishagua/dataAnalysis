package com.anshishagua.parser.nodes.bool;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/5/5
 * Time: 下午3:37
 */

public class In extends AbstractNode<Void> implements Bool {
    public In(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s IN (%s)", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new NotIn(getChild(0), getChild(1));
    }
}