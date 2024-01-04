package com.basis.evaluator.aggregator.model;

import com.basis.bid.model.BidRequest;

import java.util.Set;

public record EvaluationResult(
        BidRequest bidRequest,
        Set<String> eligibleCampaignIds) {
}
