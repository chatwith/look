package com.look.common.bean.serializer;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class SerializationException extends RuntimeException {

    public SerializationException() {
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
