package com.anshishagua.parser.semantic.function.conversion;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.conversion.ToLong;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:52
 */

public class ToLongResolver implements TypeResolver<ToLong> {
    @Override
    public void resolve(ToLong node) throws SemanticException {
        node.setResultType(BasicType.Long);
    }
}