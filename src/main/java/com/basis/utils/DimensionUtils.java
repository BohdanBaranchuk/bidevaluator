package com.basis.utils;

import com.basis.config.model.Dimension;
import com.basis.exception.BidEvaluatorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DimensionUtils {

    private static final String DIMENSIONS_ITEM = "%dx%d";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String DIMENSIONS_DELIMITER = COMMA + SPACE;
    private static final String X = "x";


    private DimensionUtils() {
    }

    public static String dimensionToString(Dimension dimension) {
        return DIMENSIONS_ITEM.formatted(dimension.length(), dimension.width());
    }

    public static String dimensionsToString(List<Dimension> dimensions) {
        return dimensions.stream()
                .map(DimensionUtils::dimensionToString)
                .collect(Collectors.joining(DIMENSIONS_DELIMITER));
    }

    public static List<Dimension> stringToDimensions(String dimensions) {
        return Arrays.stream(dimensions.split(COMMA))
                .map(DimensionUtils::stringToDimension)
                .toList();
    }

    public static Dimension stringToDimension(String value) {
        String[] parts = value.split(X);
        if (parts.length != 2) {
            throw new BidEvaluatorException("Could not split string dimension value '%s' into 2 parts".formatted(value));
        }
        try {
            var length = Integer.valueOf(parts[0].trim());
            var width = Integer.valueOf(parts[1].trim());
            return new Dimension(length, width);
        } catch (NumberFormatException ex) {
            throw new BidEvaluatorException("Could not extract length from '%s' or width from %s".formatted(parts[0], parts[1]), ex);
        }
    }
}
