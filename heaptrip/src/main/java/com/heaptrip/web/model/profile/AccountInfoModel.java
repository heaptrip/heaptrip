package com.heaptrip.web.model.profile;

public class AccountInfoModel extends AccountModel {

    private String email;

    private AccountProfileModel accountProfile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountProfileModel getAccountProfile() {
        return accountProfile;
    }

    public void setAccountProfile(AccountProfileModel accountProfile) {
        this.accountProfile = accountProfile;
    }


}
