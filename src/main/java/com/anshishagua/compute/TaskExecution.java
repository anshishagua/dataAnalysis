package com.anshishagua.compute;

import com.anshishagua.object.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:15
 */

public class TaskExecution {
    public static final long NO_ID = -1;
    private long id = NO_ID;
    private long taskId;
    private int locks;
    private Task task;
    private String executeDate;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdated;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int executionSeconds;
    private List<String> executeSQLs = new ArrayList<>();
    private String sqlString = "";
    private TaskStatus status;
    private String errorMessage = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getLocks() {
        return locks;
    }

    public void setLocks(int locks) {
        this.locks = locks;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getExecutionSeconds() {
        return executionSeconds;
    }

    public void setExecutionSeconds(int executionSeconds) {
        this.executionSeconds = executionSeconds;
    }

    public List<String> getExecuteSQLs() {
        return executeSQLs;
    }

    public void setExecuteSQLs(List<String> executeSQLs) {
        this.executeSQLs = executeSQLs;
        this.sqlString = executeSQLs.toString();
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}