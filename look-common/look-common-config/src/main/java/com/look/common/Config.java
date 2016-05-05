package com.look.common;

import com.look.common.log.LoggerConfig;
import com.look.common.utils.MixAll;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * user: xingjun.zhang
 * Date: 2016/1/3 0003
 */
public class Config {
    public static Properties properties = null;
    static final LoggerConfig config = new LoggerConfig();
    private static final String CONFIG_FILES = "logger.properties";
    public static void main(String[] args) throws IOException {
        URL url = ClassLoader.getSystemClassLoader().getResource(CONFIG_FILES);
        System.out.println(url.getPath());
        InputStream in = new BufferedInputStream(new FileInputStream(url.getPath()));
        properties = new Properties();
        properties.load(in);
        MixAll.properties2Object(properties,config);
        System.out.println(config.getModuleName());
    }
}
