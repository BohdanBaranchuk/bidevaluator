package com.basis.evaluator.aggregator.impl;

import com.basis.bid.generator.api.BidRequestsGenerator;
import com.basis.bid.generator.impl.BidRequestsGeneratorImpl;
import com.basis.bid.model.BidRequest;
import com.basis.campaign.api.CampaignsProvider;
import com.basis.campaign.impl.CampaignsProviderImpl;
import com.basis.config.api.BidConfigurationLoader;
import com.basis.config.impl.ConfigurationLoaderImpl;
import com.basis.evaluator.aggregator.api.EvaluationResultsAggregator;
import com.basis.evaluator.aggregator.model.AggregatedEvaluationResult;
import com.basis.evaluator.aggregator.model.EvaluationResult;
import com.basis.evaluator.engine.api.CampaignsEvaluationEngine;
import com.basis.evaluator.engine.impl.CampaignsEvaluationEngineImpl;
import com.basis.timemeasurer.api.TimeMeasurer;
import com.basis.timemeasurer.impl.TimeMeasurerImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EvaluationResultsAggregatorImpl implements EvaluationResultsAggregator {

    private static final BidConfigurationLoader CONFIGURATION_LOADER = ConfigurationLoaderImpl.INSTANCE;
    private final CampaignsProvider campaignsProvider = new CampaignsProviderImpl();
    private final BidRequestsGenerator bidGenerator = new BidRequestsGeneratorImpl();
    private final CampaignsEvaluationEngine evaluationEngine = new CampaignsEvaluationEngineImpl();
    private final TimeMeasurer timeMeasurer = new TimeMeasurerImpl();

    @Override
    public AggregatedEvaluationResult getAggregatedEvaluationResult() {

        var campaigns = campaignsProvider.getCampaigns();

        var bids = bidGenerator.generateFromConfiguration(CONFIGURATION_LOADER.getBidConfiguration());

        timeMeasurer.start();

        Set<EvaluationResult> evaluationResults = new HashSet<>();
        bids.forEach(bid -> findBidMatching(bid).ifPresent(evaluationResults::add));

        timeMeasurer.stop();

        return new AggregatedEvaluationResult(
                campaigns,
                evaluationResults,
                bids.size(),
                timeMeasurer.getElapsedPrettyPrintedTime());
    }

    private Optional<EvaluationResult> findBidMatching(BidRequest bid) {
        Set<String> matchedCampaignIds = evaluationEngine.evaluateBidCampaigns(bid);
        return matchedCampaignIds.isEmpty() ?
                Optional.empty() :
                Optional.of(new EvaluationResult(bid, new HashSet<>(matchedCampaignIds)));
    }
}
