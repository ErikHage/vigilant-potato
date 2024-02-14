package com.tfr.vigilant.executor;

import com.tfr.vigilant.consumer.MessageConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component("VigilantScheduler")
public class VigilantScheduler implements ApplicationListener<ContextRefreshedEvent> {

    private final MessageConsumer messageConsumer;

    public VigilantScheduler(@Qualifier("MessageConsumer") MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SturdyScheduler sturdyScheduler = new SturdyScheduler(
                "vigilant-scheduler",
                Executors.newSingleThreadScheduledExecutor());

        sturdyScheduler.scheduleWithFixedDelay(messageConsumer, 5, 5, TimeUnit.SECONDS);
    }
}
