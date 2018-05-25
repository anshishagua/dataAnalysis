package com.anshishagua.parser.nodes.function.condition;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.TripleFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 下午11:25
 */

public class If extends TripleFunctionNode {
    public If(List<Node> children) {
        super("if", children);
    }
}