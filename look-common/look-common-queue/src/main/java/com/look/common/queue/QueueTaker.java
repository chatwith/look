package com.look.common.queue;

import com.look.common.queue.QueueEntity;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface QueueTaker<T extends QueueEntity> {
    T take();

    T take(long timeout) throws InterruptedException;
}
