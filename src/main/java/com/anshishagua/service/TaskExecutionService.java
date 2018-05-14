package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.mybatis.mapper.TaskExecutionMapper;
import com.anshishagua.object.Index;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TaskStatus;
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
    @Autowired
    private TagService tagService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private ThreadPoolService threadPoolService;

    public TaskExecution getById(long id) {
        return taskExecutionMapper.getById(id);
    }

    public TaskExecution getByTask(long taskId, LocalDateTime scheduledExecutionTime) {
        return null;
    }

    public TaskExecution newTaskExecution(Task task) {
        TaskExecution taskExecution = new TaskExecution();
        taskExecution.setTaskId(task.getId());

        return taskExecution;
    }

    public void update(TaskExecution taskExecution) {
        taskExecutionMapper.update(taskExecution);
    }

    private void reSubmitTask(TaskExecution taskExecution) {
        taskExecution.setStatus(TaskStatus.RUNNING);
    }

    private void submitTask(Task task, LocalDateTime scheduledExecutionTime) {
        SQLGenerateResult sqlGenerateResult = null;

        switch (task.getTaskType()) {
            case TAG:
                Tag tag = tagService.getById(task.getObjectId());
                sqlGenerateResult = tag.getSqlGenerateResult();
                threadPoolService.submit(task, scheduledExecutionTime, sqlGenerateResult);

                break;
            case INDEX:
                Index index = indexService.getById(task.getObjectId());
                sqlGenerateResult = index.getSqlGenerateResult();
                threadPoolService.submit(task, scheduledExecutionTime, sqlGenerateResult);

                break;
            case DATA_LOAD:
                break;
            default:
                break;
        }
    }

    public void executeTask(long taskId, LocalDateTime scheduledExecutionTime) {
        Task task = taskService.getById(taskId);

        if (task == null) {
            LOG.error("Task {} not found", taskId);

            return;
        }

        List<Task> dependentTasks = taskDependencyService.getDependentTasks(task);

        for (Task dependentTask : dependentTasks) {
            TaskExecution taskExecution = getByTask(dependentTask.getId(), scheduledExecutionTime);

            if (taskExecution == null) {

            } else {
                TaskStatus taskStatus = taskExecution.getStatus();

                if (taskStatus == TaskStatus.FINISHED_SUCCESS) {
                    continue;
                } else {

                }
            }
        }
    }
}