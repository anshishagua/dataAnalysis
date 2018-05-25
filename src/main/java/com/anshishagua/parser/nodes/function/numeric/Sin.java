package com.anshishagua.parser.nodes.function.numeric;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.FunctionNode;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/23
 * Time: 下午3:51
 */

public class Sin extends UnaryFunctionNode {
    public Sin(List<Node> children) {
        super("sin", children);
    }
}