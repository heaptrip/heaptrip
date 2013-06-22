package com.heaptrip.domain.service.content;

import java.util.Locale;

import com.heaptrip.domain.entity.content.ContentEnum;

/**
 * 
 * Base class for all criterian classes
 * 
 */
public abstract class ContentCriteria {

	// content type
	protected ContentEnum contentType;

	// current user id
	protected String userId;

	// id list of categories
	protected String[] categoryIds;

	// id list of regions
	protected String[] regionIds;

	// the number of records to skip
	protected Long skip;

	// the maximum number of records
	protected Long limit;

	// how to sort
	protected ContentSortEnum sort;

	// locale
	protected Locale locale;

	public ContentEnum getContentType() {
		return contentType;
	}

	public void setContentType(ContentEnum contentType) {
		this.contentType = contentType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String[] getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(String[] regionIds) {
		this.regionIds = regionIds;
	}

	public Long getSkip() {
		return skip;
	}

	public void setSkip(Long skip) {
		this.skip = skip;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public ContentSortEnum getSort() {
		return sort;
	}

	public void setSort(ContentSortEnum sort) {
		this.sort = sort;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
