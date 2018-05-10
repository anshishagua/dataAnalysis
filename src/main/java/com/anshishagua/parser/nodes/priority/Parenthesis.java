package com.anshishagua.parser.nodes.priority;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.comparision.Equal;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Parenthesis && Objects.equals(this.getChild(0), ((Equal) obj).getChild(0));
    }
}