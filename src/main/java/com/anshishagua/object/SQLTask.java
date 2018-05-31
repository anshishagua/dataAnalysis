package com.anshishagua.object;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.constants.TaskStatus;
import com.anshishagua.constants.TaskType;
import com.anshishagua.service.IndexService;
import com.anshishagua.service.SQLExecuteService;
import com.anshishagua.service.TagService;
import com.anshishagua.service.TaskDependencyService;
import com.anshishagua.service.TaskExecutionService;
import com.anshishagua.service.TaskService;
import com.anshishagua.service.ThreadPoolService;
import com.anshishagua.utils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午11:14
 */

public class SQLTask implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(SQLTask.class);

    private TaskExecution taskExecution;

    private SQLExecuteService sqlExecuteService = ApplicationContextUtils.getBean(SQLExecuteService.class);
    private TaskExecutionService taskExecutionService = ApplicationContextUtils.getBean(TaskExecutionService.class);
    private TaskDependencyService taskDependencyService = ApplicationContextUtils.getBean(TaskDependencyService.class);
    private ThreadPoolService threadPoolService = ApplicationContextUtils.getBean(ThreadPoolService.class);
    private TaskService taskService = ApplicationContextUtils.getBean(TaskService.class);
    private TagService tagService = ApplicationContextUtils.getBean(TagService.class);
    private IndexService indexService = ApplicationContextUtils.getBean(IndexService.class);

    public SQLTask(TaskExecution taskExecution) {
        Objects.requireNonNull(taskExecution);

        this.taskExecution = taskExecution;
    }

    @Override
    public Void call() {
        List<Long> taskIds = taskDependencyService.getUpStreamTaskIds(taskExecution.getTaskId());

        for (Long taskId : taskIds) {
            TaskExecution execution = taskExecutionService.getByTask(taskId, taskExecution.getExecuteDate());

            if (execution == null || execution.getStatus() != TaskStatus.FINISHED_SUCCESS) {
                LOG.info(String.format("Date:%s, task %d wait for task %d to run success",
                        taskExecution.getExecuteDate(),
                        taskExecution.getTaskId(), execution == null ? -1 : execution.getTaskId()));

                return null;
            }
        }

        /*
        int locks = taskExecution.getLocks();

        if (locks > 0) {
            taskExecution.setStatus(TaskStatus.LOCKING);
            taskExecutionService.update(taskExecution);

            return null;
        }
        */

        taskExecution.setStartTime(LocalDateTime.now());
        taskExecution.setEndTime(null);
        taskExecution.setStatus(TaskStatus.RUNNING);
        taskExecutionService.update(taskExecution);

        String executeDate = taskExecution.getExecuteDate();

        try {
            sqlExecuteService.execute(taskExecution.getExecuteSQLs());

            LocalDateTime startTime = taskExecution.getStartTime();
            LocalDateTime endTime = LocalDateTime.now();
            taskExecution.setEndTime(endTime);
            taskExecution.setStatus(TaskStatus.FINISHED_SUCCESS);
            int executionSeconds = (int) ChronoUnit.SECONDS.between(startTime, endTime);

            taskExecution.setExecutionSeconds(executionSeconds);
            taskExecutionService.update(taskExecution);
        } catch (SQLException ex) {
            taskExecution.setErrorMessage(ex.toString());
            taskExecution.setEndTime(LocalDateTime.now());
            taskExecution.setStatus(TaskStatus.FINISHED_FAILED);
            taskExecutionService.update(taskExecution);

            LOG.error("Failed to execute sqls {}", taskExecution.getExecuteSQLs(), ex);
        }

        LOG.info("Execution done");

        List<Long> downStreamTaskIds = taskDependencyService.getDownStreamTaskIds(taskExecution.getTaskId());

        for (Long taskId : downStreamTaskIds) {
            Task task = taskService.getById(taskId);

            if (task == null) {
                continue;
            }

            TaskExecution execution = taskExecutionService.getByTask(taskId, taskExecution.getExecuteDate());

            if (execution != null) {
                execution.setLocks(execution.getLocks() - 1);

                taskExecutionService.update(execution);

                if (execution.getLocks() == 0) {
                    threadPoolService.submit(taskExecution);
                }
            } else {
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

                taskExecutionService.insert(taskExecution);

                threadPoolService.submit(taskExecution);
            }
        }

        return null;
    }
}