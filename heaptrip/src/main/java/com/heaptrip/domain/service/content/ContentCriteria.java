package com.heaptrip.domain.service.content;

import java.util.Locale;

import com.heaptrip.domain.entity.content.ContentStatusEnum;

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

	// search favorite contents by user id
	protected String favoriteUserId;

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

	public String getFavoriteUserId() {
		return favoriteUserId;
	}

	public void setFavoriteUserId(String favoriteUserId) {
		this.favoriteUserId = favoriteUserId;
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
