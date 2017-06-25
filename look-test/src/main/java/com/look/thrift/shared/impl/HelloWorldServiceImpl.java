package com.look.thrift.shared.impl;

import com.look.thrift.shared.HelloWorldService;
import org.apache.thrift.TException;

/**
 * user: xingjun.zhang
 * Date: 2016/7/17 0017
 */
public class HelloWorldServiceImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to my blog www.zxj.com";
    }
}
