package com.heaptrip.domain.repository.solr.entity;

import java.util.List;

public class SolrSearchContentResponse extends SolrSearchReponse {

	private List<SolrContent> contents;

	public List<SolrContent> getContents() {
		return contents;
	}

	public void setContents(List<SolrContent> contents) {
		this.contents = contents;
	}

}
