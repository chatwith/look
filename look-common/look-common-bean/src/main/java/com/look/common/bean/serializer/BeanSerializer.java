package com.look.common.bean.serializer;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public interface BeanSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
