package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("TestHighPriorityMessageHandler")
public class TestHighPriorityMessageHandler implements MessageHandler {

    @Override
    public Map<String, Object> handle(Message message) {
        return Map.of();
    }
}
