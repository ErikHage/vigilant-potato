package com.tfr.vigilant.model.message;

public enum MessageType {

    TEST("TEST");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
