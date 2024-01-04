package com.basis.evaluator.engine.impl;

import com.basis.bid.model.BidRequest;
import com.basis.campaign.impl.CampaignsProviderImpl;
import com.basis.campaign.model.Campaign;
import com.basis.config.model.Dimension;
import com.basis.evaluator.engine.api.CampaignsEvaluationEngine;
import com.basis.evaluator.engine.impl.model.BidRequestMatchingParams;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.basis.utils.DimensionUtils.stringToDimensions;
import static com.basis.utils.UrlUtils.getHostFromUrl;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

public class CampaignsEvaluationEngineImpl implements CampaignsEvaluationEngine {

    private final Map<String, Set<String>> countryToCampaigns;
    private final Map<String, Set<String>> domainToCampaigns;
    private final Map<Dimension, Set<String>> dimensionToCampaigns;

    public CampaignsEvaluationEngineImpl() {

        Set<Campaign> campaigns = new CampaignsProviderImpl().getCampaigns();

        // assumption is made that campaigns list could be huge and updated not to so frequently
        // in such case precomputing could bring performance busts for searching bid matching
        // if campaigns is pretty small like in this demo it is not make sense to do
        countryToCampaigns = campaigns.stream()
                .collect(groupingBy(
                        Campaign::targetedCountry,
                        mapping(Campaign::campaignId, toSet())));

        domainToCampaigns = campaigns.stream()
                .collect(groupingBy(
                        Campaign::targetedDomain,
                        mapping(Campaign::campaignId, Collectors.toSet())));

        dimensionToCampaigns = new HashMap<>();

        for (Campaign campaign : campaigns) {
            stringToDimensions(campaign.availableDimensions())
                    .forEach(dimension -> dimensionToCampaigns.computeIfAbsent(dimension, key -> new HashSet<>()).add(campaign.campaignId()));
        }
    }

    @Override
    public Set<String> evaluateBidCampaigns(BidRequest bidRequest) {

        BidRequestMatchingParams matchingParams = extractMatchingParams(bidRequest);

        Set<String> fullMatching = countryToCampaigns.get(matchingParams.country());
        if (Objects.isNull(fullMatching) || fullMatching.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> domainsMatching = domainToCampaigns.get(matchingParams.domain());
        if (Objects.isNull(domainsMatching) || domainsMatching.isEmpty()) {
            return Collections.emptySet();
        }

        fullMatching.retainAll(domainsMatching);
        if (fullMatching.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> dimensionsMatches = matchingParams.dimensions().stream()
                .flatMap(dimension -> dimensionToCampaigns.get(dimension).stream())
                .collect(toSet());

        fullMatching.retainAll(dimensionsMatches);

        return fullMatching;
    }

    private BidRequestMatchingParams extractMatchingParams(BidRequest bidRequest) {

        var country = bidRequest.originCountry();
        var host = getHostFromUrl(bidRequest.pageUrl());
        var dimensions = stringToDimensions(bidRequest.placementDimensions());

        return new BidRequestMatchingParams(country, host, dimensions);
    }
}
