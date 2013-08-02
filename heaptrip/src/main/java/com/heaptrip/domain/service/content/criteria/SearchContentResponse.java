package com.heaptrip.domain.service.content.criteria;

import java.util.List;

import com.heaptrip.domain.entity.content.Content;

public class SearchContentResponse {

	private long numFound;

	private List<Content> contents;

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

}
