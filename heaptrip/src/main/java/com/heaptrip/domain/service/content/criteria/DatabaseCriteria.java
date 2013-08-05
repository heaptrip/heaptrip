package com.heaptrip.domain.service.content.criteria;

/**
 * 
 * Base class for all of the criteria for reading from the database
 * 
 */
public abstract class DatabaseCriteria extends ContentCriteria {

	// how to sort
	protected ContentSortEnum sort;

	public ContentSortEnum getSort() {
		return sort;
	}

	public void setSort(ContentSortEnum sort) {
		this.sort = sort;
	}

}
