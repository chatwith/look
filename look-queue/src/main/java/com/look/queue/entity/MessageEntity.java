package com.look.queue.entity;

import com.look.common.queue.QueueEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public class MessageEntity implements QueueEntity, Serializable{

    private String name;
    private Integer age;
    private Date date;

    @Override
    public boolean validate() {
        if (null != name && null != age) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
