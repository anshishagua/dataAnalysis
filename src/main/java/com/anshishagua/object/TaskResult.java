package com.anshishagua.object;

import java.util.HashMap;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/7/8
 * Time: 下午1:41
 */

public class TaskResult {
    private boolean success;
    private String errorMessage;
    private Map<String, Object> properties = new HashMap<>();

    public static TaskResult ok() {
        TaskResult result = new TaskResult();
        result.success = true;

        return result;
    }

    public static TaskResult error(String errorMessage) {
        TaskResult result = new TaskResult();
        result.success = false;
        result.errorMessage = errorMessage;

        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }
}