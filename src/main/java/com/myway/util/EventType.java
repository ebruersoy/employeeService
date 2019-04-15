package com.myway.util;

/**
 * @author Ebru Göksal
 */
public enum EventType {
    ADD("Kafka_Employee_Added"),
    UPDATED("Kafka_Employee_Updated"),
    DELETED("Kafka_Employee_Deleted");

    EventType(String code) {
        this.code = code;
    }

    public boolean equals(String codeKey) {
        return code.equals(codeKey);
    }

    public String toString() {
        return this.code;
    }

    private final String code;

    public String getCode() {
        return code;
    }
}
