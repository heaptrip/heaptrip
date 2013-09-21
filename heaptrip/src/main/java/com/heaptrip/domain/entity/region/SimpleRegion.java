package com.heaptrip.domain.entity.region;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.MultiLangText;

/**
 * 
 * Simple region
 * 
 */
public class SimpleRegion extends BaseObject {

	// multilingual name of the region
	protected MultiLangText name;

	public SimpleRegion() {
		super();
	}

	public SimpleRegion(String id) {
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
