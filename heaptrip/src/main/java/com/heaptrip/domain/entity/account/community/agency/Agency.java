package com.heaptrip.domain.entity.account.community.agency;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.community.Community;

public class Agency extends Community {

	public Agency() {
		super();
		setTypeAccount(AccountEnum.AGENCY);
	}
}
