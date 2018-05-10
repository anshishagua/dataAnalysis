package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.nodes.AbstractNode;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:37
 */

public class Null extends AbstractNode<String> {
    public Null() {

    }

    @Override
    public String getValue() {
        return "NULL";
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Null;
    }
}