package com.look.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.look.common.bean.serializer.FastJsonSerializationBeanSerializer;
import com.look.common.log.ClientLogger;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.impl.ProducerBasedRocketQueuePutter;
import com.look.queue.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class Producer {

    private static ProducerBasedRocketQueuePutter putter;
    private static FastJsonSerializationBeanSerializer<QueueEntity> serializer;
    private static final Logger logger = ClientLogger.createLogger("Producer");
    public static void main(String[] args) throws InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("192.168.137.5:9876");
        serializer = new FastJsonSerializationBeanSerializer<QueueEntity>();
        putter = new ProducerBasedRocketQueuePutter(producer, serializer, "PushTopic");
        logger.info("ss ================= ");
        while (true) {
            Thread.sleep(1000);
            Random random = new Random();
            int ram = random.nextInt(100000);
            MessageEntity entity = new MessageEntity();
            entity.setAge(ram);
            entity.setName("wang_liu_" + ram);
            entity.setDate(new Date());
            putter.put(entity);
        }
    }
}
