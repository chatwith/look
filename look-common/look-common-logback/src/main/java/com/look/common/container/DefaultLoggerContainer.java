package com.look.common.container;

import com.look.common.builder.DefaultLoggerInfoBuilder;
import com.look.common.builder.LoggerInfoBuilder;
import com.look.common.model.LoggerInfo;
import com.look.common.report.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class DefaultLoggerContainer implements LoggerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoggerContainer.class);
    private static final String MONITOR_ZK_ADDRESS_KEY = "monitor.zk.addresses";
    private final AtomicBoolean hadStart = new AtomicBoolean(false);
    private LoggerInfoReporter reporter;

    @Override
    public void start() {
        if (hadStart.compareAndSet(false, true)) {
            LoggerInfoBuilder builder = createLoggerInfoBuilder();
            LoggerInfo loggerInfo = builder.build();
            LOGGER.info("success to build logger meta data {}.", loggerInfo);
            this.reporter = createLoggerInfoReporter(loggerInfo);
            reporter.report(loggerInfo);
            LOGGER.info("success to report logger meta data");
        } else {
            LOGGER.warn("logger container had started");
        }
    }

    protected LoggerInfoReporter createLoggerInfoReporter(LoggerInfo loggerInfo) {
        LoggerInfoReporter zkBasedReporter = new ZKBasedLoggerInfoReporter(DefaultConfigManager.getInstance().getAppConfig().getString(MONITOR_ZK_ADDRESS_KEY), "ulog");
        LoggerInfoReporter loggerBasedReporter = new LoggerBasedLoggerInfoReporter();
        return new LoggerInfoReporterComposite(zkBasedReporter, loggerBasedReporter);
    }

    protected LoggerInfoBuilder createLoggerInfoBuilder() {
        return new DefaultLoggerInfoBuilder();
    }

    @Override
    public void stop() {
        this.reporter.clean();
        this.hadStart.set(false);
    }
}
