package com.look.kafka.serializer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.look.kafka.QueueEntity;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 15:25
 */
public class MsgDeSerializer<QueueEntity> implements Deserializer<QueueEntity> {
    protected final ObjectMapper objectMapper;
    protected final Class<QueueEntity> targetType;
    private volatile ObjectReader reader;

    public MsgDeSerializer() {
        this((Class)((Class)null));
    }

    public MsgDeSerializer(ObjectMapper objectMapper) {
        this((Class)null, objectMapper);
    }

    public MsgDeSerializer(Class<QueueEntity> targetType) {
        this(targetType, new ObjectMapper());
        this.objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public MsgDeSerializer(Class<QueueEntity> targetType, ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "\'objectMapper\' must not be null.");
        this.objectMapper = objectMapper;
        if(targetType == null) {
            targetType = (Class<QueueEntity>) ResolvableType.forClass(this.getClass()).getSuperType().resolve();
        }

        Assert.notNull(targetType, "\'targetType\' cannot be resolved.");
        this.targetType = targetType;
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public QueueEntity deserialize(String topic, byte[] data) {
        if(this.reader == null) {
            this.reader = this.objectMapper.readerFor(this.targetType);
        }

        try {
            QueueEntity e = null;
            if(data != null) {
                e = this.reader.readValue(data);
            }
            return e;
        } catch (IOException var4) {
            throw new SerializationException("Can\'t deserialize data [" + Arrays.toString(data) + "] from topic [" + topic + "]", var4);
        }
    }

    @Override
    public void close() {

    }
}
