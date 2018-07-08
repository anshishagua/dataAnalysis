package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/7/6
 * Time: 上午10:14
 */

public enum SessionState {
    NOT_STARTED("not_started", "Session has not been started"),
    STARTING("starting", "Session is starting"),
    RUNNING("running", "Session is running"),
    IDLE("idle", "Session is waiting for input"),
    BUSY("busy", "Session is executing a statement"),
    SHUTTING_DOWN("shutting_down", "Session is shutting down"),
    ERROR("error", "Session errored out"),
    DEAD("dead", "Session has exited"),
    SUCCESS("success", "Session is successfully stopped");

    private String value;
    private String description;

    SessionState(String value, String description) {
        this.value = value;
        this.description = description;
    }


    public static SessionState parseByValue(String value) {
        for (SessionState sessionState : values()) {
            if (sessionState.value.equals(value)) {
                return sessionState;
            }
        }

        return null;
    }
}