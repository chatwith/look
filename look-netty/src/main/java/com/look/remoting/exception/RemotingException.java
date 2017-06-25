package com.look.remoting.exception;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public class RemotingException extends Exception {
    private static final long serialVersionUID = -5690687334570505110L;


    public RemotingException(String message) {
        super(message);
    }


    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
