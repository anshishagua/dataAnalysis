package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.Node;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 上午11:35
 */

public class UnaryFunctionNode extends FunctionNode {
    public UnaryFunctionNode(String name, List<Node> arguments) {
        super(name, arguments);
    }

    @Override
    public int requiredArgumentSize() {
        return 1;
    }
}