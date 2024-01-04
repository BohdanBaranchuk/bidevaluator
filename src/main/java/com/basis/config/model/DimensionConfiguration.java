package com.basis.config.model;

import java.util.List;

public record DimensionConfiguration(Integer maxSizesForOneItem, List<Dimension> sizes) {
}
