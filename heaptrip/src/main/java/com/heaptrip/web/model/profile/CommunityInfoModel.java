package com.heaptrip.web.model.profile;

public class CommunityInfoModel extends AccountInfoModel {

    private CommunityProfileModel communityProfileModel;

    public CommunityProfileModel getCommunityProfileModel() {
        return communityProfileModel;
    }

    public void setCommunityProfileModel(CommunityProfileModel communityProfileModel) {
        this.communityProfileModel = communityProfileModel;
    }
}
