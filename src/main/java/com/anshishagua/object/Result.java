package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/5/13
 * Time: 下午9:01
 */

public class Result {
    private boolean success;
    private String errorMessage;
    private String info;

    public Result(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static Result ok() {
        return new Result(true, "");
    }

    public static Result ok(String info) {
        Result result = new Result(true, "");
        result.info = info;

        return result;
    }

    public static Result error(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
