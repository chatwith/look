package com.look.common.rocket.impl.spring;

import com.look.common.rocket.QueueService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

import java.util.Map;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class ServiceSupportStopListener implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        Map<?, QueueService> serviceSupportMap = event.getApplicationContext().getBeansOfType(QueueService.class);
        for (QueueService service : serviceSupportMap.values()) {
            service.stop();
        }
    }
}
