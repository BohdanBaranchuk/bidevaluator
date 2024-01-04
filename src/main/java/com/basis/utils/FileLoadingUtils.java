package com.basis.utils;

import com.basis.config.impl.ConfigurationLoaderImpl;
import com.basis.exception.BidEvaluatorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public final class FileLoadingUtils {

    private static final Logger logger = Logger.getLogger(FileLoadingUtils.class.getName());

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private FileLoadingUtils() {
    }

    public static <T> T getFileLoadedModel(String path, Class<T> clazz) {

        String filePath = Optional.ofNullable(path)
                .map(String::trim)
                .filter(trimmedPath -> !trimmedPath.isEmpty())
                .orElseThrow(() -> new BidEvaluatorException("Could not find configuration file for null or empty path"));

        var message = "Starting load configuration from the path '%s'".formatted(filePath);
        logger.info(message);

        try (InputStream input = ConfigurationLoaderImpl.class.getClassLoader().getResourceAsStream(filePath)) {

            if (Objects.isNull(input)) {
                throw new BidEvaluatorException("Could not find specified configuration file '%s'".formatted(filePath));
            }

            String configBody = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            logger.info("Loaded configuration file body");

            return MAPPER.readValue(configBody, clazz);

        } catch (JsonProcessingException ex) {
            throw new BidEvaluatorException("Configuration file '%s' is malformed".formatted(path), ex);
        } catch (IOException ex) {
            throw new BidEvaluatorException("Could not load configuration file '%s'".formatted(path), ex);
        }
    }
}
