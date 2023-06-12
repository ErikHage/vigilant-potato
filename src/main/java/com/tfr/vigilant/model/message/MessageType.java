package com.tfr.vigilant.model.message;

public enum MessageType {

    TEST("TEST", MessagePriority.HIGH),
    TEST2("TEST2", MessagePriority.LOW);

    private final String value;
    private final MessagePriority priority;

    MessageType(String value, MessagePriority priority) {
        this.value = value;
        this.priority = priority;
    }

    public String getValue() {
        return value;
    }

    public MessagePriority getPriority() {
        return priority;
    }
}
