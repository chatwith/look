package com.look.common.rocket;

import com.look.common.queue.Queue;
import com.look.common.queue.QueueEntity;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface RocketQueue<T extends QueueEntity> extends Queue<T> {
    RocketQueuePutter<T> getPutter();

    RocketQueuePutter<T> getPutter(String producerGroup);

    RocketQueueTaker<T> getTaker(String consumerGroup);
}
