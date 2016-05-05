package com.look.common.bean.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public abstract class AbstractBeanSerializer<T> implements BeanSerializer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBeanSerializer.class);

    protected abstract T doDeserialize(byte[] bytes);

    protected abstract byte[] doSerialize(T t);

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t != null) {
            return doSerialize(t);
        }
        LOGGER.warn("the object to serialize is null.");
        return null;
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length > 0) {
            return doDeserialize(bytes);
        }
        LOGGER.warn("the bytes to deserialize is null or length is 0");
        return null;
    }

}
