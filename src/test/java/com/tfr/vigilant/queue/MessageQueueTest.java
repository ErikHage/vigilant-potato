package com.tfr.vigilant.queue;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageQueueTest {

    private final LocalDateTime dt1 = LocalDateTime.of(2023, 1, 1, 1, 0, 0);
    private final Message m_hp = new Message("some-id-1", MessageType.TEST_HP, "content", dt1);
    private final Message m_lp = new Message("some-id-2", MessageType.TEST_LP, "content", dt1);

    private MessageQueue mq;

    @BeforeEach
    public void setUp() {
        mq = new MessageQueue();
    }

    @AfterEach
    public void cleanUp() {
        mq.clear();
    }

    @Test
    public void testAdd_GivenHighPriorityMessage_ExpectAddedToHighPriorityQueue() {
        mq.add(m_hp);

        assertEquals(1, mq.highPrioritySize());
        assertEquals(0, mq.lowPrioritySize());
        assertEquals(1, mq.size());
    }

    @Test
    public void testAdd_GivenLowPriorityMessage_ExpectAddedToLowPriorityQueue() {
        mq.add(m_lp);

        assertEquals(0, mq.highPrioritySize());
        assertEquals(1, mq.lowPrioritySize());
        assertEquals(1, mq.size());
    }

    @Test
    public void testPoll_GivenMessagesInBothQueues_ExpectHighPriorityMessageFirst() {
        mq.add(m_lp);
        mq.add(m_hp);

        assertEquals(m_hp, mq.poll());
        assertEquals(m_lp, mq.poll());
    }

    @Test
    public void testGetMessageStatus_GivenMessagesInQueue_ExpectQueuedStatus() {
        mq.add(m_lp);

        assertEquals("QUEUED", mq.getMessageStatus(m_lp.messageId()));
    }

    @Test
    public void testGetMessageStatus_GivenMessagesNotInQueue_ExpectNullStatus() {
        assertNull(mq.getMessageStatus(m_lp.messageId()));
    }
}
