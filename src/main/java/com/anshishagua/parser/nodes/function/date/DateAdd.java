package com.anshishagua.parser.nodes.function.date;

import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.function.BinaryFunctionNode;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/25
 * Time: 下午2:51
 */

public class DateAdd extends BinaryFunctionNode {
    public DateAdd(List<Node> children) {
        super("date_add", children);
    }
}