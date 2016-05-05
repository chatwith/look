package com.look.common.report;


import com.look.common.model.LoggerInfo;

import java.nio.charset.Charset;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class ZKBasedLoggerInfoUtil {
    private static final String LINE = System.getProperty("line.separator");
    public static byte[] createData(LoggerInfo var1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("app_name=").append(var1.getAppNameOrEmpty()).append(LINE);
        stringBuilder.append("app_desc=").append(var1.getAppDescOrEmpty()).append(LINE);
        stringBuilder.append("app_group=").append(var1.getAppGroupOrEmpty()).append(LINE);
        stringBuilder.append("app_owner=").append(var1.getAppOwnerOrEmpty()).append(LINE);
        stringBuilder.append("project_src_path=").append(var1.getSrcPathOrEmpty()).append(LINE);
        stringBuilder.append("project_src_version=").append(var1.getSrcVersionOrEmpty()).append(LINE);
        stringBuilder.append("log_addr=").append(var1.getLogPathAsStr()).append(LINE);
        return stringBuilder.toString().getBytes(Charset.forName("UTF-8"));
    }

    public static String createPath(LoggerInfo var1) {
        return new StringBuilder().append(var1.getIp()).append("/").append(var1.getAppName()).toString();
    }
}
