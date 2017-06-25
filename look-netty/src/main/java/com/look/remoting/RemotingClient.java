package com.look.remoting;

import com.look.remoting.exception.RemotingConnectException;
import com.look.remoting.exception.RemotingSendRequestException;
import com.look.remoting.exception.RemotingTimeoutException;
import com.look.remoting.exception.RemotingTooMuchRequestException;
import com.look.remoting.netty.NettyRequestProcessor;
import com.look.remoting.protocol.RemotingCommand;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 远程通信，Client接口
 *
 * user: xingjun.zhang
 * Date: 2016/6/4 0004
 */
public interface RemotingClient extends RemotingService {
    public void updateNameServerAddressList(final List<String> addrs);


    public List<String> getNameServerAddressList();


    public RemotingCommand invokeSync(final String addr, final RemotingCommand request,
                                      final long timeoutMillis) throws InterruptedException, RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException;


    public void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis,
                            final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;


    public void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
            RemotingTimeoutException, RemotingSendRequestException;


    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
                                  final ExecutorService executor);


    public boolean isChannelWriteable(final String addr);
}
