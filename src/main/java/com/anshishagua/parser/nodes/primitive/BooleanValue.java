package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanValue && Objects.equals(this.value, ((BooleanValue) obj).value);
    }
}