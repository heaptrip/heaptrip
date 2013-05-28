package com.heaptrip.domain.entity;

/**
 * 
 * Region
 * 
 */
public class Region extends ContentRegion {

	public static final String COLLECTION_NAME = "regions";

	// id of the parent
	private String parent;

	// id list of all parents
	private String[] ancestors;

	// type of region
	private RegionEnum type;

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

	public RegionEnum getType() {
		return type;
	}

	public void setType(RegionEnum type) {
		this.type = type;
	}
}
