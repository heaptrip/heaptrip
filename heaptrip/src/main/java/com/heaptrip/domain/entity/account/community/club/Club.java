package com.heaptrip.domain.entity.account.community.club;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;

public class Club extends Account {
	
	public Club() {
		super();
		setTypeAccount(AccountEnum.CLUB);
	}
}
