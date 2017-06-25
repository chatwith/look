package com.look.kafka.serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.look.kafka.Msg;
import com.look.kafka.QueueEntity;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 15:24
 */
public class MsgSerializer<QueueEntity> implements Serializer<QueueEntity> {

    protected final ObjectMapper objectMapper;

    public MsgSerializer() {
        this(new ObjectMapper());
        this.objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public MsgSerializer(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "\'objectMapper\' must not be null.");
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, QueueEntity data) {
        try {
            byte[] ex = null;
            if(data != null) {
                ex = this.objectMapper.writeValueAsBytes(data);
            }

            return ex;
        } catch (IOException var4) {
            throw new SerializationException("Can\'t serialize data [" + data + "] for topic [" + topic + "]", var4);
        }
    }

    @Override
    public void close() {

    }
}
