package com.look.remoting.netty;

import com.look.remoting.protocol.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public interface NettyRequestProcessor {
    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
            throws Exception;
}
