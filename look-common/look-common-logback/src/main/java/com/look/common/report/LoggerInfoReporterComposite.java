package com.look.common.report;


import com.google.common.collect.Sets;
import com.look.common.model.LoggerInfo;

import java.util.Set;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class LoggerInfoReporterComposite implements LoggerInfoReporter{
    private final Set<LoggerInfoReporter> reporters = Sets.newHashSet();
    public LoggerInfoReporterComposite(LoggerInfoReporter ... reporters){
        for (LoggerInfoReporter reporter : reporters){
            addReport(reporter);
        }
    }

    public void addReport(LoggerInfoReporter reporter){
        this.reporters.add(reporter);
    }

    @Override
    public void report(LoggerInfo var1) {
        for (LoggerInfoReporter reporter : this.reporters){
            reporter.report(var1);
        }
    }

    @Override
    public void clean() {
        for (LoggerInfoReporter reporter : this.reporters){
            reporter.clean();
        }
    }
}
