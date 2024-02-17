package com.tfr.vigilant.executor.config;

import com.tfr.vigilant.consumer.MessageConsumer;
import com.tfr.vigilant.executor.SturdyScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
    }
}
