package com.tfr.vigilant.executor;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A wrapper for ScheduledExecutorService that fixes the bear trap problem. If a scheduled command
 * throws an exception, it is caught and logged without being rethrown. The scheduler will execute
 * the command again as scheduled.
 */
public class SturdyScheduler {
    private final String threadName;
    private final ScheduledExecutorService scheduledExecutorService;

    public  final AtomicBoolean isRunning = new AtomicBoolean(false);

    public SturdyScheduler(String threadName, ScheduledExecutorService scheduledExecutorService) {
        this.threadName = threadName;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public ScheduledFuture<?> schedule(
            Runnable command,
            long initialDelay,
            TimeUnit unit) {
        return scheduledExecutorService.schedule(
                () -> runWithExceptionHandling(command),
                initialDelay,
                unit
        );
    }

    public ScheduledFuture<?> scheduleAtFixedRate(
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

    public ScheduledFuture<?> scheduleWithFixedDelay(
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

    public String getName() {
        return threadName;
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return scheduledExecutorService.shutdownNow();
    }

    public boolean awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return scheduledExecutorService.awaitTermination(timeout, timeUnit);
    }
}
