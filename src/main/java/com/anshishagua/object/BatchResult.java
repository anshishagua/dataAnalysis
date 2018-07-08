package com.anshishagua.object;

import com.alibaba.fastjson.annotation.JSONField;
import com.anshishagua.constants.SessionState;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/7/4
 * Time: 下午4:55
 */

public class BatchResult {
    public static class AppInfo {
        private String driverLogUrl;
        private String sparkUiUrl;

        public String getDriverLogUrl() {
            return driverLogUrl;
        }

        public void setDriverLogUrl(String driverLogUrl) {
            this.driverLogUrl = driverLogUrl;
        }

        public String getSparkUiUrl() {
            return sparkUiUrl;
        }

        public void setSparkUiUrl(String sparkUiUrl) {
            this.sparkUiUrl = sparkUiUrl;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "driverLogUrl='" + driverLogUrl + '\'' +
                    ", sparkUiUrl='" + sparkUiUrl + '\'' +
                    '}';
        }
    }

    private long id;
    private String state;
    private SessionState sessionState;
    private String appId;
    private AppInfo appInfo;
    @JSONField(name = "log")
    private List<String> logs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.sessionState = SessionState.parseByValue(state);
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs.stream().map(it -> it.trim()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "BatchResult{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", appId='" + appId + '\'' +
                ", appInfo=" + appInfo +
                ", logs=" + logs +
                '}';
    }
}