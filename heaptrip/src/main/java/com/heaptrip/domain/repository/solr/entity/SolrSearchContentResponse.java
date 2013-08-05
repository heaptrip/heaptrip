package com.heaptrip.domain.repository.solr.entity;

import java.util.List;

public class SolrSearchContentResponse {

	private Long numFound;

	private List<SolrContent> contents;

	public Long getNumFound() {
		return numFound;
	}

	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}

	public List<SolrContent> getContents() {
		return contents;
	}

	public void setContents(List<SolrContent> contents) {
		this.contents = contents;
	}

}
