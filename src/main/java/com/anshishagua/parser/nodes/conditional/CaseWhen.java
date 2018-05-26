package com.anshishagua.parser.nodes.conditional;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:30
 */

public class CaseWhen extends AbstractNode<Void> {
    private List<Node> whenNodes;
    private List<Node> thenNodes;
    private Node elseNode;

    public CaseWhen(List<Node> whenNodes, List<Node> thenNodes, Node elseNode) {
        Objects.requireNonNull(whenNodes);
        Objects.requireNonNull(thenNodes);
        Objects.requireNonNull(elseNode);

        for (int i = 0; i < whenNodes.size(); ++i) {
            Objects.requireNonNull(whenNodes.get(i));
            Objects.requireNonNull(thenNodes.get(i));
        }

        this.whenNodes = new ArrayList<>(whenNodes);
        this.thenNodes = new ArrayList<>(thenNodes);
        this.elseNode = elseNode;

        List<Node> children = new ArrayList<>(whenNodes.size() + thenNodes.size() + 1);
        children.addAll(whenNodes);
        children.addAll(thenNodes);
        children.add(elseNode);
        setChildren(children);
    }

    public List<Node> getWhenNodes() {
        return Collections.unmodifiableList(whenNodes);
    }

    public List<Node> getThenNodes() {
        return Collections.unmodifiableList(thenNodes);
    }

    public Node getElseNode() {
        return elseNode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("CASE ");

        for (int i = 0; i < whenNodes.size(); ++i) {
            builder.append("WHEN ").append(whenNodes.get(i)).append(" THEN ").append(thenNodes.get(i)).append(" ");
        }

        builder.append("ELSE ").append(elseNode).append(" END");

        return builder.toString();
    }
}