package com.basis.timemeasurer.impl;

import com.basis.exception.BidEvaluatorException;
import com.basis.timemeasurer.api.TimeMeasurer;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class TimeMeasurerImpl implements TimeMeasurer {

    private static final String TIME_FORMAT = "%02d ms";
    private Instant start;
    private Instant finish;
    private Duration timeElapsed;

    @Override
    public void start() {
        start = Instant.now();
    }

    @Override
    public void stop() {
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish);
    }

    @Override
    public String getElapsedPrettyPrintedTime() {

        if (Objects.isNull(start) || Objects.isNull(finish)) {
            throw new BidEvaluatorException("Elapsed time could not be evaluated. Timer was not started or finished");
        }

        return TIME_FORMAT.formatted(timeElapsed.toMillis());
    }
}
