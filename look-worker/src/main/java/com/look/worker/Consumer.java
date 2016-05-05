package com.look.worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.look.common.bean.serializer.FastJsonSerializationBeanSerializer;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.impl.ProducerBasedRocketQueuePutter;
import com.look.common.rocket.impl.PushConsumerBasedRocketQueueTaker;
import com.look.queue.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static PushConsumerBasedRocketQueueTaker taker;
    private static FastJsonSerializationBeanSerializer<QueueEntity> serializer;
    public static void main(String[] args) throws InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("192.168.137.6:9876");
        serializer = new FastJsonSerializationBeanSerializer<QueueEntity>();
        taker = new PushConsumerBasedRocketQueueTaker(consumer, serializer, "PushTopic");
        logger.info("start ================= ");
        while (true) {
            Thread.sleep(1000);
            QueueEntity queueEntity = taker.take();
            if(queueEntity!=null){
                MessageEntity entity = (MessageEntity)queueEntity;
                logger.info("entity => name:"+ entity.getName()+",age:"+entity.getAge()+",date:"+entity.getDate());
            }

        }
    }
}
