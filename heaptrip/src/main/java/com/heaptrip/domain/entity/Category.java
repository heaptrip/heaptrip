package com.heaptrip.domain.entity;

public class Category extends ContentCategory {

	private String parentId;

	private String[] ancestors;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String[] getAncestors() {
		return ancestors;
	}

	public void setAncestors(String[] ancestors) {
		this.ancestors = ancestors;
	}
}
