package com.anshishagua.parser.semantic.function.conversion;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.conversion.ToInteger;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:51
 */

public class ToIntegerResolver implements TypeResolver<ToInteger> {
    @Override
    public void resolve(ToInteger node) throws SemanticException {
        node.setResultType(BasicType.Integer);
    }
}
