package com.heaptrip.domain.entity.account.community.company;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;

public class Company extends Account {

	public Company() {
		super();
		setTypeAccount(AccountEnum.COMPANY);
	}
}
