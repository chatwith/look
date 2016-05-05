package com.look.common.queue;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface Queue<T extends QueueEntity> {
    QueuePutter<T> getPutter();

    QueueTaker<T> getTaker(String name);
}
