package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;

public class User extends Account<UserProfile, UserSetting> {

    private String[] roles;

    private SocialNetwork[] net;

    private String extImageStore;

    private Byte[] imageCRC;

    public User() {
        super();
        setStatus(AccountStatusEnum.NOTCONFIRMED);
        setProfile(new UserProfile());
        setSetting(new UserSetting());
    }

    @Override
    public AccountEnum getAccountType() {
        return AccountEnum.USER;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public SocialNetwork[] getNet() {
        return net;
    }

    public void setNet(SocialNetwork[] net) {
        this.net = net;
    }

    public Byte[] getImageCRC() {
        return imageCRC;
    }

    public void setImageCRC(Byte[] imageCRC) {
        this.imageCRC = imageCRC;
    }

    public String getExternalImageStore() {
        return extImageStore;
    }

    public void setExtImageStore(String extImageStore) {
        this.extImageStore = extImageStore;
    }
}
