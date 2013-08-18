package com.heaptrip.service.account;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.system.SolrException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.AccountSearchService;
import com.heaptrip.domain.service.account.criteria.AccountSearchCriteria;
import com.heaptrip.domain.service.account.criteria.AccountSearchReponse;
import com.heaptrip.domain.service.system.ErrorService;

@Service
public class AccountSearchServiceImpl implements AccountSearchService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SolrAccountRepository solrAccountRepository;

	@Autowired
	private ErrorService errorService;

	@Async
	@Override
	public void saveAccount(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		try {
			Account account = accountRepository.findOne(accountId);
			// TODO dikma: convert account to solrAccount
			SolrAccount solrAccount = null;
			solrAccountRepository.save(solrAccount);
		} catch (SolrServerException | IOException e) {
			throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
		}
	}

	@Async
	@Override
	public void removeAccount(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		try {
			solrAccountRepository.remove(accountId);
		} catch (SolrServerException | IOException e) {
			throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
		}
	}

	@Override
	public AccountSearchReponse findAccountsByAccountSearchCriteria(AccountSearchCriteria criteria) {
		Assert.notNull(criteria, "criteria must not be null");
		Assert.notNull(criteria.getQuery(), "query text must not be null");
		Assert.notNull(criteria.getLocale(), "locale must not be null");

		SolrAccountSearchReponse response = null;
		try {
			response = solrAccountRepository.findByAccountSearchCriteria(criteria);
		} catch (SolrServerException e) {
			throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
		}

		AccountSearchReponse result = new AccountSearchReponse();
		result.setNumFound(response.getNumFound());
		// TODO dikma: convert response.accountIds to result.accounts

		return result;
	}

}
