package com.look.common.rocket.impl;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.look.common.bean.serializer.BeanSerializer;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.QueueService;
import com.look.common.rocket.QueueServiceImpl;
import com.look.common.rocket.RocketQueuePutter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class ProducerBasedRocketQueuePutter<T extends QueueEntity>
        extends QueueServiceImpl implements RocketQueuePutter<T>, QueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerBasedRocketQueuePutter.class);
    private static final RandomShardExtractor RANDOM_SHARD_EXTRACTOR = new RandomShardExtractor();
    private static final NullMsgExtractor NULL_MSG_EXTRACTOR = new NullMsgExtractor();
    private final DefaultMQProducer producer;
    private final BeanSerializer<T> beanSerializer;
    private final String topic;

    public ProducerBasedRocketQueuePutter(DefaultMQProducer producer, BeanSerializer<T> beanSerializer, String topic) {
        this.producer = producer;
        this.beanSerializer = beanSerializer;
        this.topic = topic;
    }

    @Override
    public boolean put(T t) {
        MsgExtractor msgExtractor = NULL_MSG_EXTRACTOR;
        if (t instanceof MsgExtractor) {
            msgExtractor = (MsgExtractor) t;
        }
        return put(t, msgExtractor);
    }

    @Override
    public boolean put(T t, MsgExtractor<T> msgExtractor) {
        ShardExtractor shardExtractor = RANDOM_SHARD_EXTRACTOR;
        if (t instanceof ShardExtractor) {
            shardExtractor = (ShardExtractor) t;
        }
        return put(t, msgExtractor, shardExtractor);
    }

    @Override
    public boolean put(T t, MsgExtractor<T> msgExtractor, ShardExtractor<T> shardExtractor) {
        start();
        if (t == null) {
            LOGGER.warn("task to put into queue is null");
            return false;
        }
        if (!t.validate()) {
            LOGGER.warn("failed to validate task {}.", t);
            return false;
        }
        String tag = msgExtractor.tag(t);
        String key = msgExtractor.key(t);
        Integer shard = shardExtractor.shard(t);
        Message msg =
                new Message(this.topic, tag, key, this.beanSerializer.serialize(t));
        try {
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, shard);
            LOGGER.info("send msg key({}), tag({}), msgId({}) result is {}.",
                    key, tag, sendResult.getMsgId(), sendResult.getSendStatus());
            return sendResult.getSendStatus() == SendStatus.SEND_OK;
        } catch (Exception e) {
            LOGGER.error("failed to send msg, error is {}.", e, e);
        }
        return false;
    }

    @Override
    public void doStart() throws RuntimeException {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            LOGGER.error("failed to start producer, error is {}.", e, e);
        }
    }

    @Override
    public void doStop() throws RuntimeException {
        this.producer.shutdown();
    }

    private static class RandomShardExtractor<T extends QueueEntity> implements ShardExtractor<T> {
        private final Random random = new Random();

        @Override
        public int shard(T t) {
            return Math.abs(random.nextInt());
        }
    }

    private static class NullMsgExtractor<T extends QueueEntity> implements MsgExtractor<T> {
        @Override
        public String key(T t) {
            return String.valueOf(new Date().getTime());
        }

        @Override
        public String tag(T t) {
            return t.getClass().getSimpleName() + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
    }
}
