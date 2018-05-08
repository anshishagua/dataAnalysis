package com.anshishagua.parser.nodes;

import com.anshishagua.parser.BasicType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/19
 * Time: 下午10:19
 */

public abstract class AbstractNode<T> implements Node {
    public static final int NO_INDEX = -1;

    protected T value;
    protected BasicType resultType;
    protected Node parent;
    protected int index = NO_INDEX;

    private List<Node> children = new ArrayList<>();

    public AbstractNode() {

    }

    public AbstractNode(List<Node> children) {
        Objects.requireNonNull(children);

        for (int i = 0; i < children.size(); ++i) {
            Objects.requireNonNull(children.get(i));
        }

        this.children = new ArrayList<>(children);

        for (int i = 0; i < this.children.size(); ++i) {
            Node node = children.get(i);

            node.setIndex(i);
            node.setParent(this);
        }
    }

    public AbstractNode(Node ...  children) {
        this(Arrays.asList(children));
    }

    public AbstractNode(T value) {
        Objects.requireNonNull(value);

        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChild(int index, Node child) {
        Objects.requireNonNull(child);

        if (index < 0 || index >= children.size()) {
            throw new IndexOutOfBoundsException();
        }

        child.setParent(this);
        child.setIndex(index);

        children.set(index, child);
    }

    public void setChildren(Node ... children) {
        Objects.requireNonNull(children);

        setChildren(Arrays.asList(children));
    }

    public void setChildren(List<Node> children) {
        Objects.requireNonNull(children);

        this.children = new ArrayList<>(children);

        for (int i = 0; i < children.size(); ++i) {
            Node child = children.get(i);

            child.setIndex(i);
            child.setParent(this);
        }
    }

    public Node getChild(int i) {
        if (i < 0 || i >= children.size()) {
            throw new IllegalArgumentException();
        }

        return children.get(i);
    }

    @Override
    public BasicType getResultType() {
        return resultType;
    }

    public void setResultType(BasicType resultType) {
        this.resultType = resultType;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Node)) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Node node = (Node) obj;

        if (!Objects.equals(this.value, ((AbstractNode) node).getValue())) {
            return false;
        }

        if (this.children.size() != node.getChildren().size()) {
            return false;
        }

        for (int i = 0; i < this.children.size(); ++i) {
            if (!this.children.get(i).equals(((AbstractNode) node).getChild(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Node negate() {
        return this;
    }
}