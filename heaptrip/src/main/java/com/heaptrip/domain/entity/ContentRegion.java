package com.heaptrip.domain.entity;

public class ContentRegion extends BaseObject {

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
