package com.anshishagua.parser.nodes.arithmetic;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:25
 */

public class Plus extends AbstractNode<Void> {
    public Plus(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s + %s", getChild(0), getChild(1));
    }
}