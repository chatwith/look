package com.look.common.log;

/**
 * user: xingjun.zhang
 * Date: 2016/1/3 0003
 */
public class LoggerConfig {

    /**
     * 项目名称
     */
    private String moduleName;

    /**
     * 项目所有者
     */
    private String moduleUser;

    /**
     * 日志输出路径
     */
    private String logPath;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUser() {
        return moduleUser;
    }

    public void setModuleUser(String moduleUser) {
        this.moduleUser = moduleUser;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
