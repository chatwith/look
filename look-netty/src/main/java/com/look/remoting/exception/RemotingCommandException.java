package com.look.remoting.exception;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public class RemotingCommandException extends RemotingException {
    private static final long serialVersionUID = -6061365915274953096L;


    public RemotingCommandException(String message) {
        super(message, null);
    }


    public RemotingCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
