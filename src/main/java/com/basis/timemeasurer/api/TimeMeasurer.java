package com.basis.timemeasurer.api;

public interface TimeMeasurer {

    void start();

    void stop();

    String getElapsedPrettyPrintedTime();
}
