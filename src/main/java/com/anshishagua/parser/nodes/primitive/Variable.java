package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.nodes.AbstractNode;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:46
 */

public class Variable extends AbstractNode<String> {
    public Variable(String varName) {
        super(varName);
    }

    public String getVarName() {
        return getValue();
    }

    @Override
    public String toString() {
        return getValue();
    }
}