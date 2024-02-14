package com.tfr.vigilant.executor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface SafeExecutor {

    String getName();

    void shutdown();

    List<Runnable> shutdownNow();

    boolean awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException;
}
