package com.look.producer;

import com.look.common.log.ClientLogger;
import com.look.common.queue.QueueEntity;
import com.look.common.queue.QueuePutter;
import com.look.common.queue.QueueTaker;
import com.look.queue.entity.MessageEntity;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Type;

/**
 * user: xingjun.zhang
 * Date: 2016/1/6 0006
 */
public class QueueContext {

    private static final Logger LOGGER = ClientLogger.createLogger("QueueContext");
    private static final String[] CONFIG_FILES = new String[]{
            "classpath:queue_config/common.xml",
            "classpath*:message-queue.xml"
    };

    private ClassPathXmlApplicationContext applicationContext;

    private QueueContext() {
        try {
            applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);
            applicationContext.registerShutdownHook();
        } catch (Exception e) {
            LOGGER.error("Mq's springContext init error, system exit", e);
            System.exit(1);
        }
    }

    public static QueueContext getInstance() {
        return BeanFactoryHolder.FACTORY;
    }

    private static class BeanFactoryHolder {
        private static final QueueContext FACTORY = new QueueContext();
    }


    public QueuePutter<MessageEntity> getQueuePutter(String queueName, Class<?> clazz) {
        if (MessageEntity.class == (Type) clazz) {
            return (QueuePutter<MessageEntity>) applicationContext.getBean(queueName);
        }

        return null;
    }

    public QueueEntity getQueueEntity(String queueName, Class<?> clazz) {
        if (MessageEntity.class == (Type) clazz) {
            QueueTaker<MessageEntity> taker = (QueueTaker<MessageEntity>) applicationContext.getBean(queueName);
            return taker.take();
        }

        return null;
    }
}
