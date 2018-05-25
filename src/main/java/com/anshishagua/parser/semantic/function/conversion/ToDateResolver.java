package com.anshishagua.parser.semantic.function.conversion;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.BasicType;
import com.anshishagua.parser.nodes.function.conversion.ToDate;
import com.anshishagua.parser.semantic.TypeResolver;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:38
 */

public class ToDateResolver implements TypeResolver<ToDate> {
    @Override
    public void resolve(ToDate node) throws SemanticException {
        node.setResultType(BasicType.Date);
    }
}