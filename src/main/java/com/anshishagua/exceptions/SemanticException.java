package com.anshishagua.exceptions;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午3:29
 */

public class SemanticException extends RuntimeException {
    public SemanticException() {
        super();
    }

    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticException(Throwable cause) {
        super(cause);
    }

    public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}