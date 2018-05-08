package com.anshishagua.parser.nodes.primitive;

import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.utils.StringUtils;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:25
 */

public class StringValue extends AbstractNode<String> {
    public StringValue(String value) {
        super(value);

        setResultType(BasicType.String);
    }

    @Override
    public String toString() {
        return StringUtils.singleQuote(getValue());
    }

    @Override
    public boolean isConstant() {
        return true;
    }
}