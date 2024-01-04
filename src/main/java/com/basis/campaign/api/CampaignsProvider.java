package com.basis.campaign.api;

import com.basis.campaign.model.Campaign;

import java.util.Set;

public interface CampaignsProvider {

    Set<Campaign> getCampaigns();
}
