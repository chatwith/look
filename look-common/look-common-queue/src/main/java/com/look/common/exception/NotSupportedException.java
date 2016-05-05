package com.look.common.exception;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class NotSupportedException extends RuntimeException {

    public NotSupportedException() {
    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }
}
