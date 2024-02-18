package com.tfr.vigilant.executor.schedule;

import com.tfr.vigilant.executor.SturdyScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VigilantSchedulerTest {

    @Mock
    private ContextRefreshedEvent contextRefreshedEvent;

    private final SturdyScheduler sturdyScheduler = new SturdyScheduler(
            "vigilant-scheduler-test",
            Executors.newSingleThreadScheduledExecutor()
    );
    private final VigilantScheduler scheduler = new VigilantScheduler(null, sturdyScheduler);

    @Test
    void shouldStartTheSchedulerOnContextRefreshedEvent() {
        scheduler.onApplicationEvent(contextRefreshedEvent);

        assertTrue(sturdyScheduler.isRunning.get());
    }
}