package com.heaptrip.domain.entity.account.community.club;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.community.Community;

public class Club extends Community {

    public Club() {
        super();
    }

    @Override
    public AccountEnum getAccountType() {
        return AccountEnum.CLUB;
    }
}
