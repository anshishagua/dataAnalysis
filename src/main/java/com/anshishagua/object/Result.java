package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/5/13
 * Time: 下午9:01
 */

public class Result {
    private boolean success;
    private String errorMessage;

    public Result(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static Result ok() {
        return new Result(true, "");
    }

    public static Result error(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
