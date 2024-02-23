package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LogMessageHandler implements MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogMessageHandler.class);

    @Override
    public Map<String, Object> handle(Message message) {
        logger.info(message.content());

        return Map.of("message", message.content());
    }
}
