package com.tfr.vigilant.executor;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A wrapper for ScheduledExecutorService that fixes the bear trap problem. If a scheduled command
 * throws an exception, it is caught and logged without being rethrown. The scheduler will execute
 * the command again as scheduled.
 */
public class SturdyScheduler implements SafeExecutor {
    private final String threadName;
    private final ScheduledExecutorService scheduledExecutorService;

    public SturdyScheduler(String threadName, ScheduledExecutorService scheduledExecutorService) {
        this.threadName = threadName;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    ScheduledFuture<?> schedule(
            Runnable command,
            long initialDelay,
            TimeUnit unit) {
        return scheduledExecutorService.schedule(
                () -> runWithExceptionHandling(command),
                initialDelay,
                unit
        );
    }

    ScheduledFuture<?> scheduleAtFixedRate(
            Runnable command,
            long initialDelay,
            long period,
            TimeUnit unit) {
        return scheduledExecutorService.scheduleAtFixedRate(
                () -> runWithExceptionHandling(command),
                initialDelay,
                period,
                unit
        );
    }

    ScheduledFuture<?> scheduleWithFixedDelay(
            Runnable command,
            long initialDelay,
            long delay,
            TimeUnit unit) {
        return scheduledExecutorService.scheduleWithFixedDelay(
                () -> runWithExceptionHandling(command),
                initialDelay,
                delay,
                unit
        );
    }

    private static void runWithExceptionHandling(final Runnable command)
    {
        try
        {
            command.run();
        }
        catch (final Exception e)
        {
            System.out.println("Failed, boo");
            // log some stuff
        }
    }

    @Override
    public String getName() {
        return threadName;
    }

    @Override
    public void shutdown() {
        scheduledExecutorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return scheduledExecutorService.shutdownNow();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return scheduledExecutorService.awaitTermination(timeout, timeUnit);
    }
}
