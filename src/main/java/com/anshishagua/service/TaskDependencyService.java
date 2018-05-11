package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskDependency;
import com.anshishagua.compute.TaskDependentGraph;
import com.anshishagua.compute.TaskDependentGraph.DependencyTreeNode;
import com.anshishagua.mybatis.mapper.TaskDependencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    public List<TaskDependency> getAllDependencies() {
        return taskDependencyMapper.getAll();
    }

    public List<Task> getDependentTasks(Task task) {
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
}