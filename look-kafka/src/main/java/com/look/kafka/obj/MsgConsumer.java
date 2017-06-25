package com.look.kafka.obj;

import com.look.kafka.QueueEntity;
import com.look.kafka.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 13:29
 */
@Service
public class MsgConsumer {

    private final static Logger logger = LoggerFactory.getLogger(MsgConsumer.class);

    @KafkaListener(topics = "#{'${topicOne:foo1,zxj-msg}'.split(',')}", group = Utils.CONSUMER_GROUP_MSG
            , containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<Integer, QueueEntity> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("consumer : {} ", message);
        }
    }
}
