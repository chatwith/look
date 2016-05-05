package com.look.common.container.adapter;


import com.look.common.container.DefaultLoggerContainer;
import com.look.common.container.LoggerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.Plugin;


/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class PlayPlugin extends Plugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayPlugin.class);
    private final LoggerContainer loggerContainer = new DefaultLoggerContainer();
    public PlayPlugin(Application application){
//        System.out.println(application);
        LOGGER.debug("success to init play plugin using {}.", application);
    }

    public PlayPlugin(play.api.Application application){
//        System.out.println(application);
        LOGGER.debug("success to init play plugin using {}.", application);
    }
    @Override
    public void onStart() {
        this.loggerContainer.start();
    }

    @Override
    public void onStop() {
        this.loggerContainer.stop();
    }

    @Override
    public boolean enabled() {
        return true;
    }
}
