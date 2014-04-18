package com.heaptrip.domain.entity.account.community.company;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.community.Community;

public class Company extends Community {

    public Company() {
        super();
    }

    @Override
    public AccountEnum getAccountType() {
        return AccountEnum.COMPANY;
    }
}
