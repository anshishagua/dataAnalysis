package com.anshishagua.object;

import com.anshishagua.compute.TaskExecution;
import com.anshishagua.service.SQLExecuteService;
import com.anshishagua.service.TaskExecutionService;
import com.anshishagua.utils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public SQLTask(TaskExecution taskExecution) {
        Objects.requireNonNull(taskExecution);

        this.taskExecution = taskExecution;
    }

    @Override
    public Void call() {
        List<String> sqls = taskExecution.getExecuteSQLs();

        taskExecution.setStartTime(LocalDateTime.now());
        try {
            sqlExecuteService.execute(sqls);

            LocalDateTime startTime = taskExecution.getStartTime();
            LocalDateTime endTime = LocalDateTime.now();
            taskExecution.setEndTime(endTime);
            taskExecution.setStatus(TaskStatus.FINISHED_SUCCESS);
            int executionSeconds = (int) ChronoUnit.SECONDS.between(startTime, endTime);

            taskExecution.setExecutionSeconds(executionSeconds);
            taskExecutionService.update(taskExecution);
        } catch (SQLException ex) {
            taskExecution.setStatus(TaskStatus.FINISHED_FAILED);
            taskExecutionService.update(taskExecution);

            LOG.error("Failed to execute sqls {}", sqls, ex);
        }

        LOG.info("Execution done");

        return null;
    }
}
