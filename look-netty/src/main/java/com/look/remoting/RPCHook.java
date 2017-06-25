package com.look.remoting;

import com.look.remoting.protocol.RemotingCommand;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public interface RPCHook {
    public void doBeforeRequest(final String remoteAddr, final RemotingCommand request);


    public void doAfterResponse(final String remoteAddr, final RemotingCommand request,
                                final RemotingCommand response);
}
