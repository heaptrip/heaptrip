package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.BaseObject;

/**
 * 
 * Content category
 * 
 */
public class ContentCategory extends BaseObject {

	// multilingual name of the category
	protected MultiLangText name;

	protected ContentCategory() {
		super();
	}

	public ContentCategory(String id) {
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
