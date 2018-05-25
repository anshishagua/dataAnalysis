package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.Node;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:26
 */

public class TripleFunctionNode extends FunctionNode {
    public TripleFunctionNode(String name, List<Node> arguments) {
        super(name, arguments);
    }

    @Override
    public int requiredArgumentSize() {
        return 3;
    }
}