package com.heaptrip.repository.solr;

import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.SolrContext;
import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;

@Service
public class SolrAccountRepositoryImpl implements SolrAccountRepository {

	private static final Logger logger = LoggerFactory.getLogger(SolrAccountRepositoryImpl.class);

	@Autowired
	private SolrContext solrContext;

	@Override
	public void save(SolrAccount account) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();

		// set id
		if (StringUtils.isNotBlank(account.getId())) {
			doc.addField("id", account.getId());
		} else {
			throw new IllegalArgumentException("account id must not be null");
		}

		// set name
		if (StringUtils.isNotBlank(account.getName())) {
			doc.addField("name", account.getName());
		} else {
			throw new IllegalArgumentException("account name must not be null");
		}

		// set class
		if (StringUtils.isNotBlank(account.getClazz())) {
			doc.addField("class", account.getClazz());
		} else {
			throw new IllegalArgumentException("account class must not be null");
		}

		// set categories
		if (ArrayUtils.isNotEmpty(account.getCategories())) {
			doc.addField("categories", account.getCategories());
		}

		// set regions
		if (ArrayUtils.isNotEmpty(account.getRegions())) {
			doc.addField("regions", account.getRegions());
		}

		// set friends
		if (ArrayUtils.isNotEmpty(account.getFriends())) {
			doc.addField("friends", account.getFriends());
		}

		// set publishers
		if (ArrayUtils.isNotEmpty(account.getPublishers())) {
			doc.addField("publishers", account.getPublishers());
		}

		// set owners
		if (ArrayUtils.isNotEmpty(account.getOwners())) {
			doc.addField("owners", account.getOwners());
		}

		// set staff
		if (ArrayUtils.isNotEmpty(account.getStaff())) {
			doc.addField("staff", account.getStaff());
		}

		// set members
		if (ArrayUtils.isNotEmpty(account.getMembers())) {
			doc.addField("members", account.getMembers());
		}

		logger.debug("Prepare document for add to solr: {}", doc);

		SolrServer core = solrContext.getCore(SolrContext.ACCOUNTS_CORE);

		UpdateResponse response = core.add(doc);
		logger.debug("Response to adding a document: {}", response);

		response = core.commit();
		logger.debug("Response to committing a document: {}", response);
	}

	@Override
	public void remove(String accountId) throws SolrServerException, IOException {
		Assert.notNull(accountId, "accountId must not be null");

		SolrServer core = solrContext.getCore(SolrContext.ACCOUNTS_CORE);

		UpdateResponse response = core.deleteById(accountId);
		logger.debug("Response to removing a document with id={}: {}", accountId, response);

		response = core.commit();
		logger.debug("Response to committing a remove: {}", response);
	}

	@Override
	public boolean exists(String accountId) throws SolrServerException {
		Assert.notNull(accountId, "accountId must not be null");

		SolrQuery query = new SolrQuery();
		query.set("q", "id:" + accountId);
		query.set("fl", "id");

		if (logger.isDebugEnabled()) {
			logger.debug("find account query: {}", query);
		}

		SolrServer core = solrContext.getCore(SolrContext.ACCOUNTS_CORE);
		QueryResponse response = core.query(query);
		SolrDocumentList results = response.getResults();

		if (logger.isDebugEnabled()) {
			String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
					results.size());
			logger.debug(msg);
		}

		return (results.getNumFound() > 0);
	}

	@Override
	public SolrAccountSearchReponse findByAccountSearchCriteria(AccountTextCriteria criteria)
			throws SolrServerException {
		SolrQuery query = new SolrQuery();

		// set q
		query.set("q", criteria.getQuery());

		// set fq
		String fq = null;

		// set fq for class
		if (criteria.getAccountType() != null) {
			fq = "class:" + criteria.getAccountType().getClazz();
			query.add("fq", fq);
		}

		// set fq for categories
		fq = getFilterQuery("categories", criteria.getCategories());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for regions
		fq = getFilterQuery("regions", criteria.getRegions());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for friends
		fq = getFilterQuery("friends", criteria.getFriends());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for subscribers
		fq = getFilterQuery("publishers", criteria.getPublishers());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for owners
		fq = getFilterQuery("owners", criteria.getOwners());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for staff
		fq = getFilterQuery("staff", criteria.getStaff());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set fq for members
		fq = getFilterQuery("members", criteria.getMembers());
		if (StringUtils.isNotEmpty(fq)) {
			query.add("fq", fq);
		}

		// set start
		if (criteria.getSkip() != null) {
			query.set("start", criteria.getSkip().toString());
		}
		// set rows
		if (criteria.getLimit() != null) {
			query.set("rows", criteria.getLimit().toString());
		}

		// set fl
		query.set("fl", "id");

		// set defType
		query.set("defType", "dismax");

		// set qf
		query.set("qf", "name");

		if (logger.isDebugEnabled()) {
			logger.debug("find accounts query: {}", query);
		}

		SolrServer core = solrContext.getCore(SolrContext.ACCOUNTS_CORE);
		QueryResponse response = core.query(query);
		SolrDocumentList results = response.getResults();

		if (logger.isDebugEnabled()) {
			String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
					results.size());
			logger.debug(msg);
		}

		String[] accountIds = new String[results.size()];

		for (int i = 0; i < results.size(); ++i) {
			// read doc
			SolrDocument doc = results.get(i);

			// set account id
			accountIds[i] = (String) doc.getFieldValue("id");
		}

		SolrAccountSearchReponse result = new SolrAccountSearchReponse();
		result.setNumFound(results.getNumFound());
		result.setAccountIds(accountIds);

		return result;
	}

	private String getFilterQuery(String fieldName, IDListCriteria fieldCriteria) {
		String fq = null;
		if (fieldCriteria != null && ArrayUtils.isNotEmpty(fieldCriteria.getIds())) {
			for (String id : fieldCriteria.getIds()) {
				if (fq == null) {
					if (fieldCriteria.getCheckMode() == null || fieldCriteria.getCheckMode().equals(CheckModeEnum.IN)) {
						fq = fieldName + ":(" + id;
					} else {
						fq = "!" + fieldName + ":(" + id;
					}
				} else {
					fq += " OR " + id;
				}
			}
			fq += ")";
		}
		return fq;
	}
}
