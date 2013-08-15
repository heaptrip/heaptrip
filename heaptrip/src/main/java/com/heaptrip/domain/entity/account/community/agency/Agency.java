package com.heaptrip.domain.entity.account.community.agency;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;

public class Agency extends Account {

	public Agency() {
		super();
		setTypeAccount(AccountEnum.AGENCY);
	}
}
