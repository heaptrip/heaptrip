package com.heaptrip.domain.repository.solr.entity;

public abstract class SolrDocument {

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
