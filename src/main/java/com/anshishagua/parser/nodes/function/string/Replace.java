package com.anshishagua.parser.nodes.function.string;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.TripleFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 上午10:40
 */

public class Replace extends TripleFunctionNode {
    public Replace(List<Node> children) {
        super("replace", children);
    }
}