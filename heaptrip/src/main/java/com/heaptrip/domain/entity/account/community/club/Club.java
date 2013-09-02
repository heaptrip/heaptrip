package com.heaptrip.domain.entity.account.community.club;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.community.Community;

public class Club extends Community {
	
	private String[] members;
	
	public Club() {
		super();
		setTypeAccount(AccountEnum.CLUB);
		setMembers(new String[0]);
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}
}
