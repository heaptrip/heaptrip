package com.heaptrip.domain.entity.region;

import com.heaptrip.domain.entity.MultiLangText;


/**
 * 
 * Region
 * 
 */
public class Region extends SimpleRegion {

	public static final String COLLECTION_NAME = "regions";

	// id of the parent
	private String parent;

	// id list of all parents
	private String[] ancestors;

	// type of region
	private RegionEnum type;

	// multilingual full name (path) of the region
	protected MultiLangText path;

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

	public MultiLangText getPath() {
		return path;
	}

	public void setPath(MultiLangText path) {
		this.path = path;
	}

}
