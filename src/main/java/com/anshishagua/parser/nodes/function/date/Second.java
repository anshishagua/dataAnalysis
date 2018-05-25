package com.anshishagua.parser.nodes.function.date;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:50
 */

public class Second extends UnaryFunctionNode {
    public Second(List<Node> children) {
        super("second", children);
    }
}