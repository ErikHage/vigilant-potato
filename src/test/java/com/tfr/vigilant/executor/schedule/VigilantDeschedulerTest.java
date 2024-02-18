package com.tfr.vigilant.executor.schedule;

import com.tfr.vigilant.executor.SturdyScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.event.ContextStoppedEvent;

import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertFalse;

class VigilantDeschedulerTest {

    @Mock
    private ContextStoppedEvent contextStoppedEvent;

    private final SturdyScheduler sturdyScheduler = new SturdyScheduler(
            "vigilant-descheduler-test",
            Executors.newSingleThreadScheduledExecutor()
    );
    private final VigilantDescheduler descheduler = new VigilantDescheduler(sturdyScheduler);

    @Test
    void shouldStopTheSchedulerOnContextRefreshedEvent() {
        sturdyScheduler.isRunning.set(true);

        descheduler.onApplicationEvent(contextStoppedEvent);
        assertFalse(sturdyScheduler.isRunning.get());
    }
}