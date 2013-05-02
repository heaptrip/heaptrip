package com.heaptrip.domain.entity;

public class ContentCategory extends BaseObject {

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
