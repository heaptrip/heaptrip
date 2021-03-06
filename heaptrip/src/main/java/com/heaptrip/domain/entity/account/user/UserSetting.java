package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Setting;

public class UserSetting extends Setting {

    private Boolean adsFromClub;

    private Boolean adsFromCompany;

    private Boolean adsFromAgency;

    public Boolean getAdsFromClub() {
        return adsFromClub;
    }

    public void setAdsFromClub(Boolean adsFromClub) {
        this.adsFromClub = adsFromClub;
    }

    public Boolean getAdsFromCompany() {
        return adsFromCompany;
    }

    public void setAdsFromCompany(Boolean adsFromCompany) {
        this.adsFromCompany = adsFromCompany;
    }

    public Boolean getAdsFromAgency() {
        return adsFromAgency;
    }

    public void setAdsFromAgency(Boolean adsFromAgency) {
        this.adsFromAgency = adsFromAgency;
    }
}
