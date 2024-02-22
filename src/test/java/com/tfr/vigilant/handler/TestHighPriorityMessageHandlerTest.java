package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TestHighPriorityMessageHandlerTest {

    private final TestHighPriorityMessageHandler handler = new TestHighPriorityMessageHandler();

    @Test
    void shouldReturnEmptyMap() {
        Message message = new Message("id", MessageType.TEST_HP, "content", LocalDateTime.now());

        assertTrue(handler.handle(message).isEmpty());
    }
}