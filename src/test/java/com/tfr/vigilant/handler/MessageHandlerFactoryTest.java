package com.tfr.vigilant.handler;

import com.tfr.vigilant.config.MessageHandlerMap;
import com.tfr.vigilant.model.message.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MessageHandlerFactoryTest {

    @Mock
    private MessageHandlerMap messageHandlerMap;

    private MessageHandlerFactory messageHandlerFactory;
    private AutoCloseable ac;

    @BeforeEach
    void setUp() {
        ac = MockitoAnnotations.openMocks(this);

        messageHandlerFactory = new MessageHandlerFactory(messageHandlerMap);
    }

    @AfterEach
    void tearDown() throws Exception {
        ac.close();
    }

    @Test
    void shouldReturnAppropriateHandlers() {
        when(messageHandlerMap.get(MessageType.TEST_HP)).thenReturn(new TestHighPriorityMessageHandler());

        assertTrue(messageHandlerFactory.getHandler(MessageType.TEST_HP) instanceof TestHighPriorityMessageHandler);
    }
}