package com.look.common.report;


import com.look.common.model.LoggerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class LoggerBasedLoggerInfoReporter implements LoggerInfoReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerBasedLoggerInfoReporter.class);

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void report(final LoggerInfo var1) {
        this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                LOGGER.warn("logger info is {}", var1);
            }
        }, 0l, 1l, TimeUnit.MINUTES);
    }

    @Override
    public void clean() {
        scheduledExecutorService.shutdown();
    }
}
