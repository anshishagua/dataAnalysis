package com.anshishagua.parser.nodes.function.string;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.MultiFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 上午11:51
 */

public class Concat extends MultiFunctionNode {
    public static final String FUNCTION_NAME = "concat";

    public Concat(List<Node> children) {
        super(FUNCTION_NAME, children);
    }
}