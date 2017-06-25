package com.look.kafka;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 14:41
 */
public class Msg implements QueueEntity {

    public int intValue;

    public Long longValue;

    public String stringValue;

    private PType type = PType.ONE;

    public Map<Short, List<String>> complexStruct;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Msg that = (Msg) o;
        return intValue == that.intValue &&
                Objects.equals(longValue, that.longValue) &&
                Objects.equals(stringValue, that.stringValue) &&
                Objects.equals(complexStruct, that.complexStruct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue, longValue, stringValue, complexStruct);
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Map<Short, List<String>> getComplexStruct() {
        return complexStruct;
    }

    public void setComplexStruct(Map<Short, List<String>> complexStruct) {
        this.complexStruct = complexStruct;
    }

    public PType getType() {
        return type;
    }

    public void setType(PType type) {
        this.type = type;
    }
}
