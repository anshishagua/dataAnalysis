package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.Node;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/23
 * Time: 下午3:51
 */

public class Sin extends FunctionNode {
    public Sin(List<Node> children) {
        super("sin", children);
    }
}