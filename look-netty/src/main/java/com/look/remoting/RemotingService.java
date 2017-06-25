package com.look.remoting;

/**
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public interface RemotingService {

    public void start();


    public void shutdown();


    public void registerRPCHook(RPCHook rpcHook);
}
