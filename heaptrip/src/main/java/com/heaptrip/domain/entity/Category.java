package com.heaptrip.domain.entity;

public class Category extends ContentCategory {

	public static final String COLLECTION_NAME = "categories";

	private String parent;

	private String[] ancestors;

	public Category() {
		super();
	}

	public Category(String id, String parent, String[] ancestors, String nameRu, String nameEn) {
		super();
		this.id = id;
		this.parent = parent;
		this.ancestors = ancestors;
		this.name = new MultiLangText(nameRu, nameEn);
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String[] getAncestors() {
		return ancestors;
	}

	public void setAncestors(String[] ancestors) {
		this.ancestors = ancestors;
	}
}
