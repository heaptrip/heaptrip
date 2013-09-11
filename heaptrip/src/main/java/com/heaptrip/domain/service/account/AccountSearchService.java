package com.heaptrip.domain.service.account;

import com.heaptrip.domain.service.account.criteria.AccountSearchCriteria;
import com.heaptrip.domain.service.account.criteria.AccountSearchReponse;

/**
 * 
 * Service for indexing and context search accounts in Apache Solr
 * 
 */
public interface AccountSearchService {

	/**
	 * Add account to Apache Solr by accountId
	 * 
	 * @param accountId
	 */
	public void saveAccount(String accountId);
	
	/**
	 * update user to Apache Solr by accountId
	 * 
	 * @param accountId
	 */
	public void updateUser(String userId);

	/**
	 * Remove account from Apache Solr by accountId
	 * 
	 * @param accountId
	 */
	public void removeAccount(String accountId);

	/**
	 * Search account by AccountSearchCriteria
	 * 
	 * @param AccountSearchCriteria
	 *            criteria
	 * 
	 * @return account search response
	 */
	public AccountSearchReponse findAccountsByAccountSearchCriteria(AccountSearchCriteria criteria);
}
