package com.look.remoting.exception;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public class RemotingTimeoutException extends RemotingException {
    private static final long serialVersionUID = 4106899185095245979L;


    public RemotingTimeoutException(String message) {
        super(message);
    }


    public RemotingTimeoutException(String addr, long timeoutMillis) {
        this(addr, timeoutMillis, null);
    }


    public RemotingTimeoutException(String addr, long timeoutMillis, Throwable cause) {
        super("wait response on the channel <" + addr + "> timeout, " + timeoutMillis + "(ms)", cause);
    }
}
