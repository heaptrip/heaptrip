package com.heaptrip.domain.entity;

public class Region extends ContentRegion {
	public static final String COLLECTION_NAME = "regions";

	private String parent;

	private String[] ancestors;

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

	public static String getCollectionName() {
		return COLLECTION_NAME;
	}

}
