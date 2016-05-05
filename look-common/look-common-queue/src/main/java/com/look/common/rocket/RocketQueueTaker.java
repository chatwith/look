package com.look.common.rocket;

import com.look.common.queue.QueueEntity;
import com.look.common.queue.QueueTaker;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface RocketQueueTaker<T extends QueueEntity> extends QueueTaker<T> {

}
