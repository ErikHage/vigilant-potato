package com.tfr.vigilant.consumer;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageType;
import com.tfr.vigilant.queue.MessageQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class MessageConsumerTest {

    @Mock
    public MessageQueue mq;

    private MessageConsumer mc;
    private AutoCloseable ac;

    private final Message message = new Message(
            "some-id",
            MessageType.TEST_LP,
            "content",
            LocalDateTime.now());

    @BeforeEach
    public void setUp() {
        ac = MockitoAnnotations.openMocks(this);
        mc = new MessageConsumer(mq);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        ac.close();
    }

    @Test
    public void shouldConsumeAllMessagesBeforeReturning() {
        when(mq.poll()).thenReturn(message, message, null);
        when(mq.isEmpty()).thenReturn(false, false, true);

        mc.run();

        verify(mq, times(3)).isEmpty();
        verify(mq, times(2)).poll();
    }

    @Test
    public void shouldReturnWhenNoMessages() {
        when(mq.poll()).thenReturn(null);
        when(mq.isEmpty()).thenReturn(true);

        mc.run();

        verify(mq, times(1)).isEmpty();
        verify(mq, times(0)).poll();
    }
}
