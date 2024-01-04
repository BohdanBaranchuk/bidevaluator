package com.basis.evaluator.engine.impl.model;

import com.basis.config.model.Dimension;

import java.util.List;

public record BidRequestMatchingParams(
        String country,
        String domain,
        List<Dimension> dimensions) {
}
