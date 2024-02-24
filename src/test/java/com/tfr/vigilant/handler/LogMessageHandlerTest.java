package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogMessageHandlerTest {

    private final LogMessageHandler handler = new LogMessageHandler();

    @Test
    void shouldLogAndReturnTheMessage() {
        final Message message = new Message("id", MessageType.LOG, "this is a message", LocalDateTime.now());

        Map<String, Object> result = handler.handle(message);

        assertTrue(result.containsKey("message"));
        assertEquals("this is a message", result.get("message"));
    }
}