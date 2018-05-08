package com.anshishagua.parser.semantic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.nodes.Node;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午4:52
 */

public interface TypeResolver<T extends Node> {
    void resolve(T node) throws SemanticException;
}