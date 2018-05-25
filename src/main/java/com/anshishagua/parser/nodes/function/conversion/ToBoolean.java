package com.anshishagua.parser.nodes.function.conversion;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午3:34
 */

public class ToBoolean extends UnaryFunctionNode {
    public ToBoolean(List<Node> children) {
        super("to_boolean", children);
    }
}