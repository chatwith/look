package com.look.common.bean.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class FastJsonSerializationBeanSerializer<T> extends AbstractBeanSerializer<T> implements BeanSerializer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonSerializationBeanSerializer.class);

    public FastJsonSerializationBeanSerializer() {
    }

    @Override
    protected T doDeserialize(byte[] bytes) {
        String jsonStr = null;
        try {
            jsonStr = new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("failed to new string, error is {}.", e, e);
        }
        return (T) JSON.parse(jsonStr);
    }

    @Override
    protected byte[] doSerialize(T t) {
        String json = JSON.toJSONString(t, SerializerFeature.WriteClassName);
        if (json != null && !json.isEmpty()) {
            try {
                return json.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("failed to get byte, error is {}.", e, e);
            }
        }
        return null;
    }
}

