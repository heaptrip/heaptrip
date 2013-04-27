package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.MultiLangText;

public class Route {

	private MultiLangText description;

	private RoutePhoto[] photos;

	public MultiLangText getDescription() {
		return description;
	}

	public void setDescription(MultiLangText description) {
		this.description = description;
	}

	public RoutePhoto[] getPhotos() {
		return photos;
	}

	public void setPhotos(RoutePhoto[] photos) {
		this.photos = photos;
	}
}
