package com.look.common.rocket.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.look.common.bean.serializer.BeanSerializer;
import com.look.common.queue.AbstractQueueTaker;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.QueueService;
import com.look.common.rocket.RocketQueueTaker;
import org.springframework.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class PushConsumerBasedRocketQueueTaker<T extends QueueEntity> extends AbstractQueueTaker<T>
        implements RocketQueueTaker<T>, QueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushConsumerBasedRocketQueueTaker.class);
    private final DefaultMQPushConsumer consumer;
    private final String topic;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<byte[]>(1);

    public PushConsumerBasedRocketQueueTaker(DefaultMQPushConsumer consumer, BeanSerializer<T> beanSerializer, String topic) {
        super(beanSerializer);
        this.consumer = consumer;
        this.topic = topic;
    }

    @Override
    public byte[] doTake() {
        start();
        try {
            return queue.take();
        } catch (InterruptedException e) {
            LOGGER.error("failed to take from queue, error is {}.", e,e);
        }
        return null;
    }

    @Override
    public byte[] doTake(long timeout) throws InterruptedException {
        start();
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized void start() throws RuntimeException {
        if (this.started.compareAndSet(false, true)) {
            try {
                this.consumer.subscribe(topic, "*");
                this.consumer.registerMessageListener(new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                        if (!CollectionUtils.isEmpty(msgs)) {
                            for (MessageExt msg : msgs) {
                                try {
                                    queue.put(msg.getBody());
                                } catch (InterruptedException e) {
                                    LOGGER.error("failed to put into queue, error is {}.", e, e);
                                }
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });
                this.consumer.start();
            } catch (MQClientException e) {
                LOGGER.error("failed to subscribe {} {}, error is {}.", topic, "*", e, e);
            }
        }
    }

    @Override
    public synchronized void stop() throws RuntimeException {
        if (started.compareAndSet(true, false)) {
            consumer.shutdown();
        }
    }
}
