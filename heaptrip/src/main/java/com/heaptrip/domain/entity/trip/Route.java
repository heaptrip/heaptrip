package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.content.MultiLangText;

public class Route extends BaseObject {

	private MultiLangText description;

	private String map;

	public MultiLangText getDescription() {
		return description;
	}

	public void setDescription(MultiLangText description) {
		this.description = description;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}
