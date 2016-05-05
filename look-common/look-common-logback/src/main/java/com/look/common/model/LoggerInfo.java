package com.look.common.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class LoggerInfo {
    private String ip;
    private String appName;
    private String appDesc;
    private String appGroup;
    private String appOwner;
    private String srcPath;
    private String srcVersion;
    private List<String> logPath;

    public String getAppNameOrEmpty() {
        return this.getOrEmpty(this.getAppName());
    }

    private String getOrEmpty(String data) {
        return StringUtils.isEmpty(data)?"":data;
    }

    public String getAppDescOrEmpty() {
        return this.getOrEmpty(this.getAppDesc());
    }

    public String getAppGroupOrEmpty() {
        return this.getOrEmpty(this.getAppGroup());
    }

    public String getAppOwnerOrEmpty() {
        return this.getOrEmpty(this.getAppOwner());
    }

    public String getSrcPathOrEmpty(){
        return this.getOrEmpty(this.getSrcPath());
    }

    public String getSrcVersionOrEmpty(){
        return this.getOrEmpty(getSrcVersion());
    }

    public String getLogPathAsStr() {
        if(CollectionUtils.isEmpty(this.getLogPath())) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < this.logPath.size(); ++i) {
                stringBuilder.append(this.logPath.get(i));
                if(i != this.logPath.size() - 1) {
                    stringBuilder.append(",");
                }
            }

            return stringBuilder.toString().replace("//", "/");
        }
    }

    public LoggerInfo() {
    }

    public String getAppName() {
        return this.appName;
    }

    public String getAppDesc() {
        return this.appDesc;
    }

    public String getAppGroup() {
        return this.appGroup;
    }

    public String getAppOwner() {
        return this.appOwner;
    }

}
