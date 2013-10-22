package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.StatusModel;

public class AccountInfoModel extends AccountModel {

    private String email;

    private ProfileModel profile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }


}
