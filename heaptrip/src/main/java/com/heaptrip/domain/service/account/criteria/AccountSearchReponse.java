package com.heaptrip.domain.service.account.criteria;

import java.util.List;

import com.heaptrip.domain.entity.account.Account;

/**
 * 
 * Response to context search accounts
 * 
 */
public class AccountSearchReponse {

	// The total number of accounts found
	private long numFound;

	// Limited list of accounts
	private List<Account> accounts;

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
