package com.basis.bid.model;

public record BidRequest(
        String requestId,
        String originCountry,
        String pageUrl,
        String placementDimensions) {
}
