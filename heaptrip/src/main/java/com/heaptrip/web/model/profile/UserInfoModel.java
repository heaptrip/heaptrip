package com.heaptrip.web.model.profile;

public class UserInfoModel extends AccountInfoModel {

    private UserProfileModel userProfile;

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

}
