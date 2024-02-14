package com.tfr.vigilant.consumer;

import com.tfr.vigilant.queue.MessageQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MessageConsumerTest {

    @Mock
    public MessageQueue mq;

    private MessageConsumer mc;
    private AutoCloseable ac;

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
    public void test() {

    }
}
