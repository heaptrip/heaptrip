package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.BaseObject;

/**
 * 
 * Content regions
 * 
 */
public class ContentRegion extends BaseObject {

	// multilingual name of the region
	protected MultiLangText name;

	public ContentRegion() {
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
