package com.look.common.builder;


import com.look.common.logback.LoggerFileNamesCollector;
import com.look.common.model.LoggerInfo;
import com.look.common.util.HostUtil;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class DefaultLoggerInfoBuilder implements LoggerInfoBuilder {
    @Override
    public LoggerInfo build() {
        DefaultConfigManager manager = DefaultConfigManager.getInstance();
        LoggerInfo loggerInfo = new LoggerInfo();
        loggerInfo.setIp(loadLocalIp());
        loggerInfo.setAppName(manager.getAppName());
        loggerInfo.setAppDesc(manager.getAppDesc());
        loggerInfo.setAppGroup(manager.getAppOrg());
        loggerInfo.setAppOwner(manager.getAppOwner());
        loggerInfo.setSrcPath(manager.getProjectSrcPath());
        loggerInfo.setSrcVersion(manager.getProjectSrcVersion());
        loggerInfo.setLogPath(LoggerFileNamesCollector.getInstance().getAllLoggerFiles());
        return loggerInfo;
    }

    private String loadLocalIp() {
        String ip = HostUtil.getLocalIp();
        if (ip != null) {
            return ip;
        }
        return HostUtils.getLocalIp();
    }
}
