package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.Photo;

public class RoutePhoto extends Photo {
	private String name;

	private String text;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
