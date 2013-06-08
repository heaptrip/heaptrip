package com.heaptrip.domain.service;

import java.util.Locale;

import com.heaptrip.domain.entity.ContentStatusEnum;

/**
 * 
 * The basic criterion for finding content
 * 
 */
public class ContentCriteria {

	// id of the content owner
	private String ownerId;

	// current user id
	protected String userId;

	// search favorite contents for current user
	protected boolean favorite;

	// id list of categories
	protected String[] categoryIds;

	// id list of regions
	protected String[] regionIds;

	// content statuses
	protected ContentStatusEnum[] status;

	// the number of records to skip
	protected Long skip;

	// the maximum number of records
	protected Long limit;

	// how to sort
	protected ContentSortEnum sort;

	// locale
	protected Locale locale;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
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

	public ContentStatusEnum[] getStatus() {
		return status;
	}

	public void setStatus(ContentStatusEnum[] status) {
		this.status = status;
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
