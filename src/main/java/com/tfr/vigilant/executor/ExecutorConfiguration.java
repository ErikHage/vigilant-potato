package com.tfr.vigilant.executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfiguration {

    private final SturdyScheduler sturdyScheduler;

    public ExecutorConfiguration() {
        this.sturdyScheduler = new SturdyScheduler(
                "vigilant-scheduler",
                Executors.newSingleThreadScheduledExecutor());
    }

    @Bean
    @Qualifier("SturdyScheduler")
    public SturdyScheduler getSturdyScheduler() {
        return sturdyScheduler;
    }
}
