package com.look.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.look.common.bean.serializer.FastJsonSerializationBeanSerializer;
import com.look.common.log.ClientLogger;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.impl.ProducerBasedRocketQueuePutter;
import com.look.queue.entity.MessageEntity;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Random;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class ProducerQueue {

    private static final Logger logger = ClientLogger.createLogger("Producer");
    public static void main(String[] args) throws InterruptedException {
        logger.info("ss ================= ");
        while (true) {
            Thread.sleep(1000);
            Random random = new Random();
            int ram = random.nextInt(100000);
            MessageEntity entity = new MessageEntity();
            entity.setAge(ram);
            entity.setName("wang_liu_" + ram);
            entity.setDate(new Date());
            QueueContext.getInstance()
                    .getQueuePutter("messageQueuePutter",MessageEntity.class).put(entity);
        }
    }
}
