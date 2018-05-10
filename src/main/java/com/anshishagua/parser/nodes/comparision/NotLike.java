package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:23
 */

public class NotLike extends AbstractNode<Void> implements CompareNode {
    public NotLike(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s NOT LIKE %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new Like(getChild(0), getChild(1));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NotLike
                && Objects.equals(this.getChild(0), ((NotLike) obj).getChild(0))
                && Objects.equals(this.getChild(1), ((NotLike) obj).getChild(1));
    }
}