package com.heaptrip.domain.entity;

/**
 * 
 * Content regions
 * 
 */
public class ContentRegion extends BaseObject {

	// multilingual name of the region
	protected MultiLangText name;

	protected ContentRegion() {
		super();
	}

	public ContentRegion(String id) {
		super();
		this.id = id;
	}

	public MultiLangText getName() {
		return name;
	}

	public void setName(MultiLangText name) {
		this.name = name;
	}

}
