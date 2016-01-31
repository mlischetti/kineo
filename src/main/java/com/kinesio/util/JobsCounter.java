package com.kinesio.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mariano on 1/31/2016.
 */
public class JobsCounter {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    public static void add() {
        COUNTER.incrementAndGet();
    }

    public static void get() {
        COUNTER.get();
    }

    public static void subtract() {
        COUNTER.decrementAndGet();
    }

}
