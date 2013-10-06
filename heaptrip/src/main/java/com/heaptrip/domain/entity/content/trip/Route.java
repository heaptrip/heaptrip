package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.MultiLangText;

/**
 * 
 * Route of trip
 * 
 */
public class Route extends BaseObject {

	// route description
	private MultiLangText text;

	// route map
	private String map;

	public MultiLangText getText() {
		return text;
	}

	public void setText(MultiLangText text) {
		this.text = text;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}