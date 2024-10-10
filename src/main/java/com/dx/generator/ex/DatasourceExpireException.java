package com.dx.generator.ex;

public class DatasourceExpireException extends RuntimeException{

    public DatasourceExpireException() {
    }

    public DatasourceExpireException(String message) {
        super(message);
    }

    public DatasourceExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatasourceExpireException(Throwable cause) {
        super(cause);
    }

    public DatasourceExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
