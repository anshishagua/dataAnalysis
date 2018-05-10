package com.anshishagua.parser.nodes.comparision;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:21
 */

public class Equal extends AbstractNode<Void> implements CompareNode {
    public Equal(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", getChild(0), getChild(1));
    }

    @Override
    public Node negate() {
        return new NotEqual(getChild(0), getChild(1));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Equal
                && Objects.equals(this.getChild(0), ((Equal) obj).getChild(0))
                && Objects.equals(this.getChild(1), ((Equal) obj).getChild(1));
    }
}