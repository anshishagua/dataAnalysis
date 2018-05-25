package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.Node;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:57
 */

public class NoArgumentFunctionNode extends FunctionNode {
    public NoArgumentFunctionNode(String name, List<Node> arguments) {
        super(name, arguments);
    }

    @Override
    public int requiredArgumentSize() {
        return 0;
    }
}