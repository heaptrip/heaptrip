package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;

public abstract class Community extends Account {

    protected String ownerAccountId;

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
