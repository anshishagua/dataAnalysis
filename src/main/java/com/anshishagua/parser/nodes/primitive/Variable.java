package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && Objects.equals(this.getVarName(), ((Variable) obj).getVarName());
    }
}