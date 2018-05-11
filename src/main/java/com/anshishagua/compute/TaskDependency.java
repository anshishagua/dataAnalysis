package com.anshishagua.compute;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:17
 */

public class TaskDependency {
    private long id;
    private long taskId;
    private long dependentTaskId;

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

    public long getDependentTaskId() {
        return dependentTaskId;
    }

    public void setDependentTaskId(long dependentTaskId) {
        this.dependentTaskId = dependentTaskId;
    }
}