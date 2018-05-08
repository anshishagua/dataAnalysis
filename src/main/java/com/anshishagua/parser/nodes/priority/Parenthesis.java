package com.anshishagua.parser.nodes.priority;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:29
 */

public class Parenthesis extends AbstractNode<Void> {
    public Parenthesis(Node child) {
        super(child);
    }

    @Override
    public String toString() {
        return String.format("(%s)", getChild(0));
    }

    @Override
    public Node negate() {
        return new Parenthesis(getChild(0).negate());
    }
}