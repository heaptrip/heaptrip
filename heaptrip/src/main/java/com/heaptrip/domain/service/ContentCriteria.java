package com.heaptrip.domain.service;

import com.heaptrip.domain.entity.ContentStatusEnum;

public class ContentCriteria {

	protected String userId;

	protected String[] categoryIds;

	protected String[] regionIds;

	protected ContentStatusEnum[] status;

	protected Long skip;

	protected Long limit;

	protected ContentSortEnum sort;

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

}
