package com.basis.saver.impl;

import com.basis.evaluator.aggregator.model.AggregatedEvaluationResult;
import com.basis.exception.BidEvaluatorException;
import com.basis.saver.api.ResultsSaver;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class ResultsSaverImpl implements ResultsSaver {

    private static final Logger logger = Logger.getLogger(ResultsSaverImpl.class.getName());

    // this path should be externalized to properties file
    private static final String FILE_NAME_PATTERN = "evaluation-results_%s.json";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void saveEvaluationResult(AggregatedEvaluationResult result) {
        String fileName = FILE_NAME_PATTERN.formatted(LocalDateTime.now().toString());
        try {
            var message = "Evaluation results will be saved into file '%s'".formatted(fileName);
            logger.info(message);

            MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(fileName).toFile(), result);
        } catch (IOException ex) {
            throw new BidEvaluatorException("Could not save evaluations results into '%s'".formatted(fileName), ex);
        }
    }
}
