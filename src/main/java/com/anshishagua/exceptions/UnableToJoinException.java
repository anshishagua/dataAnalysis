package com.anshishagua.exceptions;

/**
 * User: lixiao
 * Date: 2018/5/10
 * Time: 下午4:15
 */

public class UnableToJoinException extends Exception {
    public UnableToJoinException() {
        super();
    }

    public UnableToJoinException(String message) {
        super(message);
    }

    public UnableToJoinException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToJoinException(Throwable cause) {
        super(cause);
    }

    public UnableToJoinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}