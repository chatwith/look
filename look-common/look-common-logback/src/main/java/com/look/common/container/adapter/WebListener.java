package com.look.common.container.adapter;


import com.look.common.container.DefaultLoggerContainer;
import com.look.common.container.LoggerContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class WebListener implements ServletContextListener{
    private final LoggerContainer loggerContainer = new DefaultLoggerContainer();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loggerContainer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        loggerContainer.stop();
    }
}
