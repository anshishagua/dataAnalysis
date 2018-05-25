package com.anshishagua.parser.nodes.function.string;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:15
 */

public class Length extends UnaryFunctionNode {
    public Length(List<Node> children) {
        super("length", children);
    }
}