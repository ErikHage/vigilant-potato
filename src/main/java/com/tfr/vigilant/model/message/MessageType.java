package com.tfr.vigilant.model.message;

public enum MessageType {

    TEST_HP(MessagePriority.HIGH),
    TEST_LP(MessagePriority.LOW),
    LOG(MessagePriority.LOW);

    private final MessagePriority priority;

    MessageType(MessagePriority priority) {
        this.priority = priority;
    }

    public MessagePriority getPriority() {
        return priority;
    }
}
