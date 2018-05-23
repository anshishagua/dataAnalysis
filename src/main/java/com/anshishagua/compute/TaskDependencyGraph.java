package com.anshishagua.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/21
 * Time: 上午10:56
 */

public class TaskDependencyGraph {
    private Map<Long, Task> taskDataMap;
    private Map<Long, List<Long>> dependencyMap;

    public TaskDependencyGraph() {
        taskDataMap = new HashMap<>();
        dependencyMap = new HashMap<>();
    }

    public void addDependency(Task task, Task dependingTask) {
        Objects.requireNonNull(task);
        Objects.requireNonNull(dependingTask);

        if (!taskDataMap.containsKey(task.getId())) {
            taskDataMap.put(task.getId(), task);
        }

        if (!taskDataMap.containsKey(dependingTask.getId())) {
            taskDataMap.put(dependingTask.getId(), dependingTask);
        }

        List<Long> taskIds = dependencyMap.get(dependingTask.getId());

        if (taskIds == null) {
            taskIds = new ArrayList<>();
        }

        taskIds.add(task.getId());

        dependencyMap.put(dependingTask.getId(), taskIds);
    }

    public List<Long> getDownStreamTaskIds(long taskId) {
        return dependencyMap.get(taskId);
    }
}