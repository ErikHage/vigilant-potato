package com.tfr.vigilant.executor;

import org.jmock.lib.concurrent.DeterministicScheduler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SturdySchedulerTest {

    private final DeterministicScheduler deterministicScheduler = new DeterministicScheduler();
    private final SturdyScheduler sturdyScheduler = new SturdyScheduler(
            "sturdy-scheduler-unit-test",
            deterministicScheduler);

    @Test
    void shouldInvokeCommandOnceAfterInitialDelay() {
        AtomicInteger counter = new AtomicInteger();

        Runnable command = counter::incrementAndGet;

        sturdyScheduler.schedule(command, 50L, TimeUnit.MILLISECONDS);

        deterministicScheduler.tick(40L, TimeUnit.MILLISECONDS);
        assertEquals(0, counter.get());

        deterministicScheduler.tick(10L, TimeUnit.MILLISECONDS);
        assertEquals(1, counter.get());

        deterministicScheduler.tick(1000L, TimeUnit.MILLISECONDS);
        assertEquals(1, counter.get());
    }

    @Test
    void shouldInvokeCommandAtFixedIntervals() {
        AtomicInteger counter = new AtomicInteger();

        Runnable command = counter::incrementAndGet;

        sturdyScheduler.scheduleAtFixedRate(command, 50L, 50L, TimeUnit.MILLISECONDS);

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(1, counter.get());

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(2, counter.get());
    }

    @Test
    void shouldInvokeCommandAtIntervalsAfterDelay() {
        AtomicInteger counter = new AtomicInteger();

        Runnable command = counter::incrementAndGet;

        sturdyScheduler.scheduleWithFixedDelay(command, 50L, 50L, TimeUnit.MILLISECONDS);

        deterministicScheduler.tick(40L, TimeUnit.MILLISECONDS);
        assertEquals(0, counter.get());

        deterministicScheduler.tick(10L, TimeUnit.MILLISECONDS);
        assertEquals(1, counter.get());

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(2, counter.get());
    }

    @Test
    void shouldInvokeAtIntervalsEvenAfterFailure() {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean threwException = new AtomicBoolean(false);

        Runnable command = () -> {
            if (!threwException.get()) {
                threwException.set(true);
                throw new RuntimeException("oops");
            } else {
                counter.incrementAndGet();
            }
        };

        sturdyScheduler.scheduleWithFixedDelay(command, 50L, 50L, TimeUnit.MILLISECONDS);

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(0, counter.get());

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(1, counter.get());

        deterministicScheduler.tick(50L, TimeUnit.MILLISECONDS);
        assertEquals(2, counter.get());
    }

    @Test
    void shouldReturnName() {
        assertEquals("sturdy-scheduler-unit-test", sturdyScheduler.getName());
    }

    @Test
    void shouldInvokeShutdownOnTheWrappedScheduler() {
        assertThrows(UnsupportedOperationException.class, sturdyScheduler::shutdown);
    }

    @Test
    void shouldInvokeShutdownNowOnTheWrappedScheduler() {
        assertThrows(UnsupportedOperationException.class, sturdyScheduler::shutdownNow);
    }

    @Test
    void shouldInvokeAwaitTerminationOnTheWrappedScheduler() {
        assertThrows(UnsupportedOperationException.class, () -> {
            sturdyScheduler.awaitTermination(1L, TimeUnit.MILLISECONDS);
        });
    }
}
