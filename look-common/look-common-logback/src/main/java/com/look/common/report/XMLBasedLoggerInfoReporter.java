package com.look.common.report;


import com.google.common.io.Files;
import com.look.common.model.LoggerInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class XMLBasedLoggerInfoReporter implements LoggerInfoReporter {
    private final String fileName;

    public XMLBasedLoggerInfoReporter(String fileName) {
        this.fileName = fileName;
    }

    public void report(LoggerInfo loggerInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuilder.append("  <metadata>\n");
        stringBuilder.append("          <app-name>").append(loggerInfo.getAppNameOrEmpty()).append("</app-name>\n");
        stringBuilder.append("          <app-desc>").append(loggerInfo.getAppDescOrEmpty()).append("</app-desc>\n");
        stringBuilder.append("          <app-group>").append(loggerInfo.getAppGroupOrEmpty()).append("</app-group>\n");
        stringBuilder.append("          <app-owner>").append(loggerInfo.getAppOwnerOrEmpty()).append("</app-owner>\n");
        stringBuilder.append("          <log-addr>").append(loggerInfo.getLogPathAsStr()).append("</log-addr>\n");
        stringBuilder.append("</metadata>  \n");
        this.writToFile(stringBuilder.toString());
    }

    private void writToFile(String s) {
        File file = new File(this.fileName);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            Files.write(s, file, Charset.forName("utf-8"));
//            System.out.println(file.getAbsolutePath());
        } catch (IOException var4) {
//            var4.printStackTrace();
        }

    }

    public void clean() {
        File file = new File(this.fileName);
        if(file.exists()) {
            file.delete();
        }

    }
}
