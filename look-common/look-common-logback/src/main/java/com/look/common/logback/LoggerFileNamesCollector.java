package com.look.common.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import com.google.common.collect.Sets;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class LoggerFileNamesCollector {
    private static final LoggerFileNamesCollector INSTANCE = new LoggerFileNamesCollector();
    private LoggerFileNamesCollector(){
    }

    public static LoggerFileNamesCollector getInstance(){
        return INSTANCE;
    }

    public List<String> getAllLoggerFiles(){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        HashSet files = Sets.newHashSet();
        Iterator loggerInfo = loggerContext.getLoggerList().iterator();

        while(loggerInfo.hasNext()) {
            ch.qos.logback.classic.Logger i = (ch.qos.logback.classic.Logger)loggerInfo.next();
            Iterator appenderIterator = i.iteratorForAppenders();

            while(appenderIterator.hasNext()) {
                Appender loggingEventAppender = (Appender)appenderIterator.next();
                if(loggingEventAppender instanceof DJAppender) {
                    DJAppender fileAppender = (DJAppender)loggingEventAppender;
                    files.add(fileAppender.getFile());
                }
            }
        }
        return new ArrayList<String>(files);
    }

}
