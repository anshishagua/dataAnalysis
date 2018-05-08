package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:31
 */

public class BooleanValue extends AbstractNode<Boolean> {
    public BooleanValue(boolean value) {
        super(value);

        setResultType(BasicType.Boolean);
    }

    @Override
    public String toString() {
        return Boolean.toString(getValue()).toUpperCase();
    }

    @Override
    public boolean isConstant() {
        return true;
    }
}