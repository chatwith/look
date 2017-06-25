package com.look.remoting;

import com.look.remoting.exception.RemotingCommandException;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public interface CommandCustomHeader {
    void checkFields() throws RemotingCommandException;
}
