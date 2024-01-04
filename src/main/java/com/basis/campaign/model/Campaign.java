package com.basis.campaign.model;

public record Campaign(
        String campaignId,
        String targetedCountry,
        String targetedDomain,
        String availableDimensions) {
}
