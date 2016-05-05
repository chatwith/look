package com.look.common.container.adapter;

import com.alibaba.dubbo.container.Container;
import com.look.common.container.DefaultLoggerContainer;
import com.look.common.container.LoggerContainer;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class LogbackContainer implements Container{
    private final LoggerContainer loggerContainer = new DefaultLoggerContainer();

    @Override
    public void start() {
        this.loggerContainer.start();
    }

    @Override
    public void stop() {
        this.loggerContainer.stop();
    }
}
