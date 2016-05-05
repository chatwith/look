package com.look.worker;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.look.common.bean.serializer.FastJsonSerializationBeanSerializer;
import com.look.common.log.ClientLogger;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.impl.PushConsumerBasedRocketQueueTaker;
import com.look.queue.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class ConsumerQueue {
    private static final Logger logger = ClientLogger.createLogger("ConsumerQueue");
    public static void main(String[] args) throws InterruptedException {

        while (true) {
            Thread.sleep(1000);
            try {
                MessageEntity entity = QueueContext.getInstance()
                        .getQueueEntity("messageQueueTaker", MessageEntity.class);
                if(entity!=null){
                    logger.info("entity => name:" + entity.getName() + ",age:" + entity.getAge() + ",date:" + entity.getDate());
                }
            }catch (Exception e){
               // e.printStackTrace();
                logger.info("error =================================");
            }
        }
    }
}
