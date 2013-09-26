package com.heaptrip.domain.repository.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;

public interface SolrAccountRepository {

	public void save(SolrAccount account) throws SolrServerException, IOException;

	public void remove(String accountId) throws SolrServerException, IOException;

	public boolean exists(String accountId) throws SolrServerException;

	public SolrAccountSearchReponse findByAccountSearchCriteria(AccountTextCriteria criteria)
			throws SolrServerException;

}
