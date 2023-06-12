package com.tfr.vigilant.model.message;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    private final LocalDateTime dt1 = LocalDateTime.of(2023, 1, 1, 1, 0, 0);
    private final LocalDateTime dt2 = LocalDateTime.of(2023, 1, 1, 1, 1, 0);
    private final Message m1 = new Message("some-id-1", MessageType.TEST, "content", dt1);
    private final Message m2 = new Message("some-id-2", MessageType.TEST, "content", dt2);
    
    @Test
    public void testCompareTo_GivenBefore_ExpectNegative() {
        assertEquals(-1, m1.compareTo(m2));
    }

    @Test
    public void testCompareTo_GivenAfter_ExpectPositive() {
        assertEquals(1, m2.compareTo(m1));
    }

    @Test
    public void testCompareTo_GivenSameTimestamp_ExpectZero() {
        assertEquals(0, m1.compareTo(m1));
    }
}
