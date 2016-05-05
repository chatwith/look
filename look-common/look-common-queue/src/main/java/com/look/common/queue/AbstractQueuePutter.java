package com.look.common.queue;

import com.look.common.bean.serializer.BeanSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public abstract class AbstractQueuePutter<T extends QueueEntity> implements QueuePutter<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueuePutter.class);
    private final BeanSerializer<T> serializer;

    public AbstractQueuePutter(BeanSerializer<T> serializer) {
        this.serializer = serializer;
    }

    protected abstract boolean doPut(byte[] bytes);

    @Override
    public boolean put(T t) {
        if (t == null) {
            LOGGER.warn("task to put into queue is null");
            return false;
        }
        if (!t.validate()) {
            LOGGER.warn("failed to validate task {}.", t);
            return false;
        }
        byte[] bytes = this.serializer.serialize(t);
        if (bytes != null && bytes.length > 0) {
            return doPut(bytes);
        }
        LOGGER.warn("serialize {} result is null or length is 0", t);
        return false;
    }

}
