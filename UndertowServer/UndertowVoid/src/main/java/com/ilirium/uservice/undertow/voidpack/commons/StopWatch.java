package com.ilirium.uservice.undertow.voidpack.commons;

import java.util.concurrent.TimeUnit;

/**
 * @author DoDo
 */
public class StopWatch {

    private final long start;

    StopWatch() {
        start = System.nanoTime();
    }

    public static StopWatch start() {
        return new StopWatch();
    }

    public long getTotalTimeMillis() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
    }
}
