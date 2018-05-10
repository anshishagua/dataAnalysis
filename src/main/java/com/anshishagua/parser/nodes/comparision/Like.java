package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:24
 */

public class Like extends AbstractNode<Void> implements CompareNode {
    public Like(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s LIKE %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new NotLike(getChild(0), getChild(1));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Like
                && Objects.equals(this.getChild(0), ((Like) obj).getChild(0))
                && Objects.equals(this.getChild(1), ((Like) obj).getChild(1));
    }
}