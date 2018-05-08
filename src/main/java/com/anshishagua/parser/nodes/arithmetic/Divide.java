package com.anshishagua.parser.nodes.arithmetic;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:27
 */

public class Divide extends AbstractNode<Void> {
    public Divide(Node left, Node right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s / %s", getChild(0), getChild(1));
    }
}