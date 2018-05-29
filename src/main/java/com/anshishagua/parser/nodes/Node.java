package com.anshishagua.parser.nodes;

import com.anshishagua.parser.BasicType;

import java.util.List;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:18
 */

public interface Node extends Expression {
    List<Node> getChildren();
    BasicType getResultType();
    void setResultType(BasicType type);
    boolean isConstant();
    Node getParent();
    void setParent(Node parent);
    Node getChild(int i);
    void setChild(int i, Node child);

    //return it's index in parent's children
    int getIndex();
    void setIndex(int i);
    Node negate();
    String toSQL();
}