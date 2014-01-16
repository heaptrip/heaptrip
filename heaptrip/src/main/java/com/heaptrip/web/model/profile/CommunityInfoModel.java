package com.heaptrip.web.model.profile;

public class CommunityInfoModel extends AccountInfoModel {

    private CommunityProfileModel communityProfile;

    public CommunityProfileModel getCommunityProfile() {
        return communityProfile;
    }

    public void setCommunityProfile(CommunityProfileModel communityProfile) {
        this.communityProfile = communityProfile;
    }
}
