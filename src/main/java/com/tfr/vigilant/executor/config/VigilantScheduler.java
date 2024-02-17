package com.tfr.vigilant.executor.config;

import com.tfr.vigilant.consumer.MessageConsumer;
import com.tfr.vigilant.executor.SturdyScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("VigilantScheduler")
public class VigilantScheduler implements ApplicationListener<ContextRefreshedEvent> {

    private final MessageConsumer messageConsumer;
    private final SturdyScheduler sturdyScheduler;

    public VigilantScheduler(
            @Qualifier("MessageConsumer") MessageConsumer messageConsumer,
            @Qualifier("SturdyScheduler") SturdyScheduler sturdyScheduler
    ) {
        this.messageConsumer = messageConsumer;
        this.sturdyScheduler = sturdyScheduler;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        sturdyScheduler.scheduleWithFixedDelay(messageConsumer, 5, 5, TimeUnit.SECONDS);
    }
}
