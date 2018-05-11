package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.mybatis.mapper.TaskExecutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:57
 */

@Service
public class TaskExecutionService {
    private static final Logger LOG = LoggerFactory.getLogger(TaskExecutionService.class);

    @Autowired
    private TaskExecutionMapper taskExecutionMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;

    public TaskExecution getById(long id) {
        return taskExecutionMapper.getById(id);
    }

    public void executeTask(long taskId, LocalDateTime scheduledExecutionTime) {
        Task task = taskService.getById(taskId);

        if (task == null) {
            LOG.error("Task {} not found", taskId);

            return;
        }

        List<Task> dependentTasks = taskDependencyService.getDependentTasks(task);


    }
}