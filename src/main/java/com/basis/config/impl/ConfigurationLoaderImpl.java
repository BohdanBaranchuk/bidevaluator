package com.basis.config.impl;

import com.basis.campaign.model.Campaigns;
import com.basis.config.api.BidConfigurationLoader;
import com.basis.config.api.CampaignsConfigurationLoader;
import com.basis.config.model.BidGeneratorConfiguration;

import static com.basis.utils.FileLoadingUtils.getFileLoadedModel;

public enum ConfigurationLoaderImpl implements BidConfigurationLoader, CampaignsConfigurationLoader {
    INSTANCE;

    private static final String BID_CONFIGURATION_PATH = "bid/bid-generator-config.json";
    private static final String CAMPAIGNS_PATH = "campaign/campaign-list.json";
    private final BidGeneratorConfiguration bidGeneratorConfiguration;
    private final Campaigns campaigns;

    ConfigurationLoaderImpl() {
        bidGeneratorConfiguration = getFileLoadedModel(BID_CONFIGURATION_PATH, BidGeneratorConfiguration.class);
        campaigns = getFileLoadedModel(CAMPAIGNS_PATH, Campaigns.class);
    }

    @Override
    public BidGeneratorConfiguration getBidConfiguration() {
        return bidGeneratorConfiguration;
    }

    @Override
    public Campaigns getCampaignsConfiguration() {
        return campaigns;
    }
}
