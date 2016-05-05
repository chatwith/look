package com.look.common.bean.serializer;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class JsonLibSerializationBeanSerializer<T> extends AbstractBeanSerializer<T> implements BeanSerializer<T> {
    private final Class<T> targetClass;
    private final JsonConfig jsonConfig;

    public JsonLibSerializationBeanSerializer(Class<T> targetClass, JsonConfig jsonConfig) {
        this.targetClass = targetClass;
        this.jsonConfig = jsonConfig;
    }

    public JsonLibSerializationBeanSerializer(Class<T> targetClass) {
        this(targetClass, new JsonConfig());
    }

    @Override
    protected T doDeserialize(byte[] bytes) {
        String jsonStr = new String(bytes);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr, this.jsonConfig);
        return (T) JSONObject.toBean(jsonObject, this.targetClass);
    }

    @Override
    protected byte[] doSerialize(T t) {
        JSONObject jsonObject = JSONObject.fromObject(t);
        String json = jsonObject.toString();
        if (json != null && !json.isEmpty()) {
            return json.getBytes();
        }
        return null;
    }
}
