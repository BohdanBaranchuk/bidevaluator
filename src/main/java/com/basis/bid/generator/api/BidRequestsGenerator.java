package com.basis.bid.generator.api;

import com.basis.bid.model.BidRequest;
import com.basis.config.model.BidGeneratorConfiguration;

import java.util.List;

public interface BidRequestsGenerator {

    List<BidRequest> generateFromConfiguration(BidGeneratorConfiguration configuration);
}
