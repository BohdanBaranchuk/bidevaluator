package com.basis.config.model;

import java.util.List;

public record BidGeneratorConfiguration(
        Integer generatedItemsNumber,
        List<String> countryCodes,
        UrlConfiguration url,
        DimensionConfiguration dimension) {
}
