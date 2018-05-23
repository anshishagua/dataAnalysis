package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.mybatis.mapper.TaskExecutionMapper;
import com.anshishagua.object.Index;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.Tag;
import com.anshishagua.object.TaskStatus;
import com.anshishagua.object.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public TaskExecution getByTask(long taskId, String executeDate) {
        return taskExecutionMapper.getByTaskIdAndDate(taskId, executeDate);
    }

    public List<TaskExecution> getAll() {
        List<TaskExecution> list = taskExecutionMapper.list();

        list.forEach(it -> {
            it.setTask(taskService.getById(it.getTaskId()));
        });

        return list;
    }

    public void insert(TaskExecution taskExecution) {
        taskExecutionMapper.insert(taskExecution);
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

    public void executeTask(long taskId, String executeDate, boolean forceToReExecute) {
        Objects.requireNonNull(executeDate);

        Task task = taskService.getById(taskId);

        if (task == null) {
            LOG.error("Task {} not found", taskId);

            return;
        }

        executeTask(task, executeDate, forceToReExecute);
    }

    public void executeTasks(List<Long> taskIds, String executeDate, boolean forceToReExecute) {
        Objects.requireNonNull(taskIds);

        taskIds.forEach(taskId -> executeTask(taskId, executeDate, forceToReExecute));
    }

    public void executeTasks(List<Long> taskIds, String executeDate) {
        Objects.requireNonNull(taskIds);

        taskIds.forEach(taskId -> executeTask(taskId, executeDate, false));
    }

    public void executeTask(Task task, String executeDate, boolean forceToReExecute) {
        Objects.requireNonNull(task);

        TaskExecution taskExecution = getByTask(task.getId(), executeDate);

        if (taskExecution == null) {
            taskExecution = new TaskExecution();
            taskExecution.setCreateTime(LocalDateTime.now());
            taskExecution.setLastUpdated(LocalDateTime.now());
            taskExecution.setTaskId(task.getId());
            taskExecution.setStatus(TaskStatus.READY_TO_RUN);
            taskExecution.setExecuteDate(executeDate);
            taskExecution.setExecutionSeconds(-1);
            taskExecution.setLocks(task.getResources());

            List<String> sqls = new ArrayList<>();

            if (task.getTaskType() == TaskType.INDEX) {
                Index index = indexService.getById(task.getObjectId());

                sqls = index.getSqlGenerateResult().getExecuteSQLs();
            } else if (task.getTaskType() == TaskType.TAG) {
                Tag tag = tagService.getById(task.getObjectId());

                sqls = tag.getSqlGenerateResult().getExecuteSQLs();
            }

            taskExecution.setExecuteSQLs(sqls);

            insert(taskExecution);

            threadPoolService.submit(taskExecution);
        } else {

            if (forceToReExecute) {
                threadPoolService.submit(taskExecution);

                return;
            }

            TaskStatus taskStatus = taskExecution.getStatus();

            if (taskStatus == TaskStatus.FINISHED_FAILED) {
                //restart task
                taskExecution.setStatus(TaskStatus.READY_TO_RUN);
                taskExecution.setLastUpdated(LocalDateTime.now());
                taskExecution.setEndTime(null);
                taskExecution.setExecutionSeconds(-1);

                update(taskExecution);

                threadPoolService.submit(taskExecution);
            }
        }
    }
}