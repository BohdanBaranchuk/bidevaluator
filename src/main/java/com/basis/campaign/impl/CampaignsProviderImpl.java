package com.basis.campaign.impl;

import com.basis.campaign.api.CampaignsProvider;
import com.basis.campaign.model.Campaign;
import com.basis.config.api.CampaignsConfigurationLoader;
import com.basis.config.impl.ConfigurationLoaderImpl;

import java.util.Set;

public class CampaignsProviderImpl implements CampaignsProvider {

    private static final CampaignsConfigurationLoader CAMPAIGNS_LOADER = ConfigurationLoaderImpl.INSTANCE;

    @Override
    public Set<Campaign> getCampaigns() {
        return CAMPAIGNS_LOADER.getCampaignsConfiguration().campaigns();
    }
}
