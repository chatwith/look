package com.look.common.rocket;

import com.look.common.queue.QueueEntity;
import com.look.common.queue.QueuePutter;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface RocketQueuePutter<T extends QueueEntity> extends QueuePutter<T> {
    boolean put(T t, MsgExtractor<T> msgExtractor);

    boolean put(T t, MsgExtractor<T> msgExtractor, ShardExtractor<T> shardExtractor);

    interface TagExtractor<T extends QueueEntity> {
        String tag(T t);
    }

    interface KeyExtractor<T extends QueueEntity> {
        String key(T t);
    }

    interface ShardExtractor<T extends QueueEntity> {
        int shard(T t);
    }

    interface MsgExtractor<T extends QueueEntity> extends TagExtractor<T>, KeyExtractor<T> {

    }
}
