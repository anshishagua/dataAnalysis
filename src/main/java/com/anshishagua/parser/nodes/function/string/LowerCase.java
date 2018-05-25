package com.anshishagua.parser.nodes.function.string;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.UnaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 上午10:40
 */

public class LowerCase extends UnaryFunctionNode {
    public LowerCase(List<Node> children) {
        super("lower_case", children);
    }
}