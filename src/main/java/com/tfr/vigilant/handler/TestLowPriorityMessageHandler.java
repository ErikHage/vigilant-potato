package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;

import java.util.Map;

public class TestLowPriorityMessageHandler implements MessageHandler {

    @Override
    public Map<String, Object> handle(Message message) {
        return Map.of();
    }
}
