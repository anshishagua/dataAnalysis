package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午9:32
 */

public enum TaskStatus {
    READY_TO_RUN("READY_TO_RUN", "等待运行"),
    RUNNING("RUNNING", "运行中"),
    FINISHED_SUCCESS("FINISHED_SUCCESS", "运行成功"),
    WAIT_DATA("WAIT_DATA", "等待数据"),
    FINISHED_FAILED("FINISHED_FAILED", "运行失败"),
    UNKNOWN("UNKNOWN", "未知状态");

    private String value;
    private String description;

    TaskStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static TaskStatus parseByValue(String value) {
        for (TaskStatus taskStatus : values()) {
            if (taskStatus.value.equals(value)) {
                return taskStatus;
            }
        }

        return UNKNOWN;
    }
}