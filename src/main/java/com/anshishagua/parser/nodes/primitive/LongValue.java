package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:24
 */

public class LongValue extends AbstractNode<Long> {
    public LongValue(long value) {
        super(value);

        setResultType(BasicType.Long);
    }

    @Override
    public String toString() {
        return Long.toString(getValue());
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LongValue && Objects.equals(this.value, ((LongValue) obj).getValue());
    }
}