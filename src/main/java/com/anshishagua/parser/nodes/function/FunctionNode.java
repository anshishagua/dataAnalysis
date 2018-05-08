package com.anshishagua.parser.nodes.function;

import com.anshishagua.parser.nodes.AbstractNode;
import com.anshishagua.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午2:36
 */

public class FunctionNode extends AbstractNode<Void> {
    private String name;
    private List<Node> arguments = new ArrayList<>();

    public FunctionNode(String name, List<Node> arguments) {
        Objects.requireNonNull(name);

        this.name = name;

        if (arguments != null && !arguments.isEmpty()) {
            setChildren(arguments);
            this.arguments = getChildren();
        }
    }

    public String getName() {
        return name;
    }

    public List<Node> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(name);

        builder.append("(");

        Iterator<Node> iterator = getChildren().iterator();

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        builder.append(")");

        return builder.toString();
    }
}