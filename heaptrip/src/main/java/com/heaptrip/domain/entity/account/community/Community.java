package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;

public class Community extends Account {

    public Community() {
        super();

        setStatus(AccountStatusEnum.NOTCONFIRMED);
        setProfile(new CommunityProfile());
        setSetting(new CommunitySetting());
    }
}
