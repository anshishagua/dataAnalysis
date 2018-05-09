package com.anshishagua.object;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/3
 * Time: 上午11:46
 */

public class SQLGenerateResult {
    private boolean success;
    private String errorMessage;
    private List<String> executeSQLs = new ArrayList<>();
    private Set<String> dataSourceTables = new HashSet<>();
    private Set<String> targetTables = new HashSet<>();

    public static SQLGenerateResult error(String errorMessage) {
        SQLGenerateResult result = new SQLGenerateResult();

        result.setSuccess(false);
        result.setErrorMessage(errorMessage);

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

    public List<String> getExecuteSQLs() {
        return executeSQLs;
    }

    public void setExecuteSQLs(List<String> executeSQLs) {
        this.executeSQLs = executeSQLs;
    }

    public Set<String> getDataSourceTables() {
        return dataSourceTables;
    }

    public void setDataSourceTables(Set<String> dataSourceTables) {
        this.dataSourceTables = dataSourceTables;
    }

    public Set<String> getTargetTables() {
        return targetTables;
    }

    public void setTargetTables(Set<String> targetTables) {
        this.targetTables = targetTables;
    }
}