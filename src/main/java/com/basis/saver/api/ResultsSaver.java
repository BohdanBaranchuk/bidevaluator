package com.basis.saver.api;

import com.basis.evaluator.aggregator.model.AggregatedEvaluationResult;

public interface ResultsSaver {

    void saveEvaluationResult(AggregatedEvaluationResult result);
}
