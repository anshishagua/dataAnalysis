package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午11:12
 */

public enum TaskType {
    DATA_LOAD("DATA_LOAD", "数据加载"),
    TAG("TAG", "标签计算"),
    INDEX("INDEX", "指标计算"),
    UNKNOWN("UNKNOWN", "未知");

    private String value;
    private String description;

    TaskType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static TaskType parseByValue(String value) {
        for (TaskType taskType : values()) {
            if (taskType.value.equals(value)) {
                return taskType;
            }
        }

        return UNKNOWN;
    }

    public String getDescription() {
        return description;
    }
}