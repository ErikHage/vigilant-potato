package com.tfr.vigilant.executor.schedule;

import com.tfr.vigilant.executor.SturdyScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("VigilantDescheduler")
public class VigilantDescheduler implements ApplicationListener<ContextStoppedEvent> {

    private final SturdyScheduler sturdyScheduler;

    public VigilantDescheduler(
            @Qualifier("SturdyScheduler") SturdyScheduler sturdyScheduler
    ) {
        this.sturdyScheduler = sturdyScheduler;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextStoppedEvent event) {
        sturdyScheduler.shutdown();

        sturdyScheduler.isRunning.set(false);
    }
}
