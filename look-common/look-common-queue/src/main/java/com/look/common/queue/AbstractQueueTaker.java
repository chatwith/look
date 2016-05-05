package com.look.common.queue;

import com.look.common.bean.serializer.BeanSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public abstract class AbstractQueueTaker<T extends QueueEntity> implements QueueTaker<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueueTaker.class);
    private final BeanSerializer<T> serializer;

    public AbstractQueueTaker(BeanSerializer<T> serializer) {
        this.serializer = serializer;
    }

    protected abstract byte[] doTake();

    protected abstract byte[] doTake(long timeout) throws InterruptedException;

    @Override
    public T take() {
        byte[] bytes = doTake();
        if (bytes != null && bytes.length > 0) {
            T t = this.serializer.deserialize(bytes);
            if (t == null || !t.validate()) {
                LOGGER.warn("failed to validate {}.", t);
                return null;
            }
            return t;
        }
        LOGGER.debug("take from queue {} result is null or length is 0");
        return null;
    }

    @Override
    public T take(long timeout) throws InterruptedException {
        byte[] bytes = doTake(timeout);
        if (bytes != null && bytes.length > 0) {
            T t = this.serializer.deserialize(bytes);
            if (t == null || !t.validate()) {
                LOGGER.warn("failed to validate {}.", t);
                return null;
            }
            return t;
        }
        LOGGER.debug("take from queue {} result is null or length is 0");
        return null;
    }
}
