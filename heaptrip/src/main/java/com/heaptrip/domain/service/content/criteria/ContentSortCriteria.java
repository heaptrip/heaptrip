package com.heaptrip.domain.service.content.criteria;

/**
 * 
 * Base class for all content criteria for reading sorted data from the database
 * 
 */
public abstract class ContentSortCriteria extends ContentCriteria {

	// how to sort
	protected ContentSortEnum sort;

	public ContentSortEnum getSort() {
		return sort;
	}

	public void setSort(ContentSortEnum sort) {
		this.sort = sort;
	}

}
