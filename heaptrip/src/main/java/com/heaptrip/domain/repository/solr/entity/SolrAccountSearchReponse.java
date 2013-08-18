package com.heaptrip.domain.repository.solr.entity;

public class SolrAccountSearchReponse extends SolrSearchReponse {

	private String[] accountIds;

	public String[] getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(String[] accountIds) {
		this.accountIds = accountIds;
	}

}
