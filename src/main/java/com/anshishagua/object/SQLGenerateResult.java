package com.anshishagua.object;

import com.alibaba.fastjson.JSON;

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
    private List<String> tempTables = new ArrayList<>();

    public static SQLGenerateResult ok() {
        SQLGenerateResult result = new SQLGenerateResult();

        result.setSuccess(true);
        result.setErrorMessage("");

        return result;
    }

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

    public void addExecuteSQL(String sql) {
        this.executeSQLs.add(sql);
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

    public void addTempTable(String tempTableName) {
        this.tempTables.add(tempTableName);
    }

    public List<String> getTempTables() {
        return tempTables;
    }

    @Override
    public String toString() {
        return "SQLGenerateResult{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", executeSQLs=" + executeSQLs +
                ", dataSourceTables=" + dataSourceTables +
                ", targetTables=" + targetTables +
                ", tempTables=" + tempTables +
                '}';
    }

    public static void main(String [] args) {
        SQLGenerateResult result = SQLGenerateResult.ok();

        Set<String> targetTables = new HashSet<>();
        targetTables.add("index_1");

        result.setTargetTables(targetTables);

        Set<String> dataSourceTables = new HashSet<>();
        dataSourceTables.add("student");
        dataSourceTables.add("course");

        result.setDataSourceTables(dataSourceTables);

        List<String> executeSQLs = new ArrayList<>();
        executeSQLs.add("SELECT * from `prs_cust_info`.`p_exchange_date` = '20180408' AND (`prs_cust_info`.`sex` = '01' AND `prs_cust_info`.`area_house` > 200 AND `prs_cust_info`.`price_car` > 2000000\n");

        result.setExecuteSQLs(executeSQLs);

        String json = JSON.toJSONString(result);

        System.out.println(json);

        SQLGenerateResult sss = JSON.parseObject(json, SQLGenerateResult.class);

        System.out.println(sss);
    }
}