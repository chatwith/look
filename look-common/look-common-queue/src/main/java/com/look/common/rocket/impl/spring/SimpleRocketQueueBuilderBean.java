package com.look.common.rocket.impl.spring;

import com.look.common.bean.serializer.BeanSerializer;
import com.look.common.bean.serializer.FastJsonSerializationBeanSerializer;
import com.look.common.bean.serializer.JsonLibSerializationBeanSerializer;
import com.look.common.rocket.RocketQueue;
import com.look.common.rocket.impl.RocketQueueImpl;
import org.springframework.beans.factory.FactoryBean;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class SimpleRocketQueueBuilderBean implements FactoryBean<RocketQueue> {
    private BeanSerializer beanSerializer;
    private final String queueName;
    private Class beanClass;

    public SimpleRocketQueueBuilderBean(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public RocketQueue getObject() throws Exception {
        return new RocketQueueImpl(getBeanSerializer(), getQueueName());
    }

    @Override
    public Class<?> getObjectType() {
        return RocketQueue.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public BeanSerializer getBeanSerializer() {
        if (this.beanSerializer == null || this.beanClass != null) {
            if (this.beanClass != null) {
                this.beanSerializer = new JsonLibSerializationBeanSerializer(this.beanClass);
            } else {
                this.beanSerializer = new FastJsonSerializationBeanSerializer();
            }
        }
        return beanSerializer;
    }

    public void setBeanSerializer(BeanSerializer beanSerializer) {
        this.beanSerializer = beanSerializer;
    }

    public String getQueueName() {
        return queueName;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}