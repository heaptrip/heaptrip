package com.heaptrip.domain.repository.solr.entity;

public abstract class SolrSearchReponse {

	protected long numFound;

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

}
