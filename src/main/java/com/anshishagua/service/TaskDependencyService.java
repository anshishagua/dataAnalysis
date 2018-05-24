package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskDependency;
import com.anshishagua.compute.TaskDependencyGraph;
import com.anshishagua.compute.TaskDependentGraph;
import com.anshishagua.compute.TaskDependentGraph.DependencyTreeNode;
import com.anshishagua.mybatis.mapper.TaskDependencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:29
 */

@Service
public class TaskDependencyService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDependencyMapper taskDependencyMapper;

    private TaskDependentGraph dependentGraph = new TaskDependentGraph();
    private TaskDependencyGraph taskDependencyGraph;

    public List<TaskDependency> getAllDependencies() {
        return taskDependencyMapper.getAll();
    }

    public List<Task> getDependentTasks2(Task task) {
        DependencyTreeNode root = dependentGraph.get(task);

        if (root == null || root.getChildren().isEmpty()) {
            return Collections.emptyList();
        }

        List<Task> list = new ArrayList<>();

        Queue<DependencyTreeNode> queue = new LinkedList<>();

        for (DependencyTreeNode child : root.getChildren()) {
            queue.offer(child);
        }

        while (!queue.isEmpty()) {
            DependencyTreeNode node = queue.poll();

            list.add(node.getTask());

            for (DependencyTreeNode child : node.getChildren()) {
                queue.offer(child);
            }
        }

        Collections.reverse(list);

        return list;
    }

    public void buildTaskDependentGraph() {
        List<TaskDependency> dependencies = getAllDependencies();

        for (TaskDependency dependency : dependencies) {
            Task task = taskService.getById(dependency.getTaskId());
            Task dependentTask = taskService.getById(dependency.getDependentTaskId());

            dependentGraph.addDependency(task, dependentTask);
        }
    }

    @PostConstruct
    public void buildTaskDependencyGraph() {
        taskDependencyGraph = new TaskDependencyGraph();

        List<TaskDependency> dependencies = getAllDependencies();

        for (TaskDependency dependency : dependencies) {
            Task task = taskService.getById(dependency.getTaskId());
            Task dependentTask = taskService.getById(dependency.getDependentTaskId());

            taskDependencyGraph.addDependency(task, dependentTask);
        }
    }

    public void add(Task task, List<Task> dependentTasks) {
        Objects.requireNonNull(task);
        Objects.requireNonNull(dependentTasks);

        for (Task dependentTask : dependentTasks) {
            dependentGraph.addDependency(task, dependentTask);
            taskDependencyGraph.addDependency(task, dependentTask);

            TaskDependency dependency = new TaskDependency();
            dependency.setTaskId(task.getId());
            dependency.setDependentTaskId(dependentTask.getId());

            taskDependencyMapper.insert(dependency);
        }
    }

    public List<Long> getUpStreamTaskIds(long taskId) {
        return taskDependencyGraph.getUpStreamTaskIds(taskId);
    }

    public List<Long> getDownStreamTaskIds(long taskId) {
        return taskDependencyGraph.getDownStreamTaskIds(taskId);
    }

    public List<Task> getDependentTasks(Task task) {
        Objects.requireNonNull(task);

        DependencyTreeNode node = dependentGraph.get(task);

        if (node == null) {
            return Collections.emptyList();
        }

        return node.getChildren().stream().map(it -> it.getTask()).collect(Collectors.toList());
    }
}