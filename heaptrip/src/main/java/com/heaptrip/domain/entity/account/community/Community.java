package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;

public class Community extends Account {

    private String ownerAccountId;

    public Community() {
        super();

        setStatus(AccountStatusEnum.NOTCONFIRMED);
        setProfile(new CommunityProfile());
        setSetting(new CommunitySetting());
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }
}
