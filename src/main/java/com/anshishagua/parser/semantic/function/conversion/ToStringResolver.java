package com.anshishagua.parser.semantic.function.conversion;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.conversion.ToString;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:39
 */

public class ToStringResolver implements TypeResolver<ToString> {
    @Override
    public void resolve(ToString node) throws SemanticException {
        node.setResultType(BasicType.String);
    }
}