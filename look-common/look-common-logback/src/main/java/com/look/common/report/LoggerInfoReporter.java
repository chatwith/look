package com.look.common.report;


import com.look.common.model.LoggerInfo;

public interface LoggerInfoReporter {
    void report(LoggerInfo var1);

    void clean();
}
