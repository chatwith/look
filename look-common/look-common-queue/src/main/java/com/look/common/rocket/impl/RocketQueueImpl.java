package com.look.common.rocket.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.look.common.bean.serializer.BeanSerializer;
import com.look.common.queue.AbstractQueue;
import com.look.common.queue.QueueEntity;
import com.look.common.rocket.RocketQueue;
import com.look.common.rocket.RocketQueuePutter;
import com.look.common.rocket.RocketQueueTaker;

import java.util.HashMap;
import java.util.Map;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class RocketQueueImpl<T extends QueueEntity> extends AbstractQueue<T> implements RocketQueue<T> {
    private final String nameServer = "192.168.137.4:9876;192.168.137.5:9876;192.168.137.6:9876";//ConfigurationManager.getInstance().getString("rocketmq.nameserver");
    private final String queueName;
    private final Map<String, RocketQueueTaker> takerMap = new HashMap<String, RocketQueueTaker>();
    private final Map<String, RocketQueuePutter> putterMap = new HashMap<String, RocketQueuePutter>();

    public RocketQueueImpl(BeanSerializer<T> serializer, String queueName) {
        super(serializer);
        this.queueName = queueName;
    }

    @Override
    public synchronized RocketQueuePutter<T> getPutter(String producerGroup) {
        RocketQueuePutter putter = putterMap.get(producerGroup);
        if (putter == null) {
            putter = buildRocketQueuePutter(producerGroup);
            this.putterMap.put(producerGroup, putter);
        }
        return putter;
    }

    @Override
    public RocketQueuePutter<T> getPutter() {
        return getPutter(getDefaultProducerName());
    }

    @Override
    public synchronized RocketQueueTaker<T> getTaker(String consumerGroup) {
        RocketQueueTaker taker = this.takerMap.get(consumerGroup);
        if (taker == null) {
            taker = buildRocketQueueTaker(consumerGroup);
            this.takerMap.put(consumerGroup, taker);
        }
        return taker;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getDefaultProducerName() {
        return this.queueName;
    }

    private RocketQueueTaker buildRocketQueueTaker(String consumerGroup) {
        return new PushConsumerBasedRocketQueueTaker(buildMQPushConsumer(consumerGroup), getSerializer(), getQueueName());
    }

    private DefaultMQPushConsumer buildMQPushConsumer(String consumerGroup) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(getNameServer());
        return consumer;
    }

    private RocketQueuePutter buildRocketQueuePutter(String producerGroup) {
        return new ProducerBasedRocketQueuePutter(buildMqProducer(producerGroup), getSerializer(), getQueueName());
    }

    private DefaultMQProducer buildMqProducer(String producerGroup) {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setSendMsgTimeout(3000);
        producer.setNamesrvAddr(getNameServer());
        return producer;
    }
}