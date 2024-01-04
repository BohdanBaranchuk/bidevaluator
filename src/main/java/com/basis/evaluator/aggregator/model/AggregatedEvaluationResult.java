package com.basis.evaluator.aggregator.model;

import com.basis.campaign.model.Campaign;

import java.util.Set;

public record AggregatedEvaluationResult(
        Set<Campaign> definedCampaigns,
        Set<EvaluationResult> evaluationResults,
        Integer totalBidRequests,
        String evaluationTime) {
}
