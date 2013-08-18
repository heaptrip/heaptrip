package com.heaptrip.domain.entity.account;

import com.heaptrip.domain.entity.account.community.agency.Agency;
import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.entity.account.community.company.Company;
import com.heaptrip.domain.entity.account.user.User;

/**
 * 
 * Enumeration of accounts
 * 
 */
public enum AccountEnum {
	USER(User.class.getName()), 
	CLUB(Club.class.getName()), 
	COMPANY(Company.class.getName()), 
	AGENCY(Agency.class.getName());

	private String clazz;

	private AccountEnum(String clazz) {
		this.clazz = clazz;
	}

	public String getClazz() {
		return clazz;
	}
}