package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:33
 */

public class DoubleValue extends AbstractNode<Double> {
    public DoubleValue(double value) {
        super(value);

        setResultType(BasicType.Double);
    }

    @Override
    public String toString() {
        return Double.toString(getValue());
    }

    @Override
    public boolean isConstant() {
        return true;
    }
}