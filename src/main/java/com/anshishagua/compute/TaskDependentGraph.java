package com.anshishagua.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:22
 */

public class TaskDependentGraph {
    public static class DependencyTreeNode {
        private Task task;
        private List<DependencyTreeNode> children;
        private DependencyTreeNode parent;

        public DependencyTreeNode(Task task) {
            this.task = task;
            this.children = new ArrayList<>();
        }

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public List<DependencyTreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<DependencyTreeNode> children) {
            this.children = children;
        }

        public void addChild(DependencyTreeNode child) {
            this.children.add(child);
        }

        public DependencyTreeNode getParent() {
            return parent;
        }

        public void setParent(DependencyTreeNode parent) {
            this.parent = parent;
        }
    }

    private Map<Task, DependencyTreeNode> dependencyMap = new HashMap<>();

    public void addDependency(Task task, Task dependentTask) {
        DependencyTreeNode parent = null;
        DependencyTreeNode child = null;

        if (!dependencyMap.containsKey(task)) {
            parent = new DependencyTreeNode(task);
            dependencyMap.put(task, parent);
        } else {
            parent = dependencyMap.get(task);
        }

        if (!dependencyMap.containsKey(dependentTask)) {
            child = new DependencyTreeNode(dependentTask);

            dependencyMap.put(dependentTask, child);
        } else {
            child = dependencyMap.get(dependentTask);
        }

        parent.addChild(child);
        child.setParent(parent);
    }

    public Map<Task, DependencyTreeNode> getDependencyMap() {
        return Collections.unmodifiableMap(dependencyMap);
    }

    public DependencyTreeNode get(Task task) {
        return dependencyMap.get(task);
    }
}