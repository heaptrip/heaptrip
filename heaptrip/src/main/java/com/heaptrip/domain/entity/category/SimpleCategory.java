package com.heaptrip.domain.entity.category;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.content.MultiLangText;

/**
 * 
 * Content category
 * 
 */
public class SimpleCategory extends BaseObject {

	// multilingual name of the category
	protected MultiLangText name;

	protected SimpleCategory() {
		super();
	}

	public SimpleCategory(String id) {
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
