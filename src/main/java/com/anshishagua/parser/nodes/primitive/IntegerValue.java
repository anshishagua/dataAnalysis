package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:19
 */

public class IntegerValue extends AbstractNode<Integer> {
    public IntegerValue(int value) {
        super(value);

        setResultType(BasicType.Integer);
    }

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }
}