package com.basis;

import com.basis.evaluator.aggregator.api.EvaluationResultsAggregator;
import com.basis.evaluator.aggregator.impl.EvaluationResultsAggregatorImpl;
import com.basis.saver.api.ResultsSaver;
import com.basis.saver.impl.ResultsSaverImpl;

import java.util.logging.Logger;

public class BidEvaluatorStarter {

    private static final Logger logger = Logger.getLogger(BidEvaluatorStarter.class.getName());

    private final EvaluationResultsAggregator resultsAggregator = new EvaluationResultsAggregatorImpl();
    private final ResultsSaver resultsSaver = new ResultsSaverImpl();

    void startFlow() {

        logger.info("Staring bid requests evaluation flow");

        var result = resultsAggregator.getAggregatedEvaluationResult();

        logger.info("All bid requests evaluated");

        resultsSaver.saveEvaluationResult(result);

        logger.info("Evaluations results saved");
    }
}
