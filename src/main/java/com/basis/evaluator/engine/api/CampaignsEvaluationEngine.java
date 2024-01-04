package com.basis.evaluator.engine.api;

import com.basis.bid.model.BidRequest;

import java.util.Set;

public interface CampaignsEvaluationEngine {

    Set<String> evaluateBidCampaigns(BidRequest bidRequest);
}
