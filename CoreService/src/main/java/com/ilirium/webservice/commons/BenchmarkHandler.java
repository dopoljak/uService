package com.ilirium.webservice.commons;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class BenchmarkHandler {
    public static void benchmark(String bechmarkType, Benchmarked benchmarked) throws Exception {
        ZonedDateTime now = ZonedDateTime.now();
        benchmarked.execute();
        System.out.println("Time - " + bechmarkType + " : " + now.until(ZonedDateTime.now(), ChronoUnit.MILLIS) + "ms");
    }

    public interface Benchmarked {
        public void execute() throws Exception;
    }
}
