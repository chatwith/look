package com.look.common.queue;


import com.look.common.bean.serializer.BeanSerializer;
import com.look.common.exception.NotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public abstract class AbstractQueue<T extends QueueEntity> implements Queue<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueue.class);
    private final BeanSerializer<T> serializer;
    private QueuePutter<T> putter = null;
    private final Map<String, QueueTaker<T>> takerCacheMap = new HashMap<String, QueueTaker<T>>();

    public AbstractQueue(BeanSerializer<T> serializer) {
        this.serializer = serializer;
    }

    protected QueueTaker<T> createQueueTaker(String name){
        throw new NotSupportedException();
    }

    protected QueuePutter<T> createQueuePutter(){
        throw new NotSupportedException();
    }


    @Override
    public synchronized QueuePutter<T> getPutter() {
        if (putter == null) {
            LOGGER.debug("begin to crate new putter");
            try {
                this.putter = createQueuePutter();
                LOGGER.info("success to create new putter, result is {}.", putter);
            } catch (Exception e) {
                LOGGER.error("failed to create new putter, error is {}.", e);
            }

        }
        return this.putter;
    }

    @Override
    public QueueTaker<T> getTaker(String name) {
        QueueTaker<T> taker = this.takerCacheMap.get(name);
        if (taker == null) {
            synchronized (this.takerCacheMap) {
                taker = this.takerCacheMap.get(name);
                if (taker == null) {
                    LOGGER.debug("begin to crate new taker for {}.", name);
                    try {
                        taker = createQueueTaker(name);
                        this.takerCacheMap.put(name, taker);
                        LOGGER.info("success to create new taker for {}, result is {}.", name, taker);
                    } catch (Exception e) {
                        LOGGER.error("failed to crate new taker for {}, error is {}.", name, e);
                    }

                }
            }
        }
        return taker;
    }

    public BeanSerializer<T> getSerializer() {
        return serializer;
    }
}
