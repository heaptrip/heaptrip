package com.heaptrip.domain.entity;

public class Category extends ContentCategory {

	public static final String COLLECTION_NAME = "categories";

	private String parentId;

	private String[] ancestors;

	public Category() {
		super();
	}

	public Category(String id, String parentId, String[] ancestors, String nameRu, String nameEn) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.ancestors = ancestors;
		this.name = new MultiLangText(nameRu, nameEn);
	}

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
