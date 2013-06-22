package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.image.Image;

public class Route {

	private MultiLangText description;

	private Image[] images;

	public MultiLangText getDescription() {
		return description;
	}

	public void setDescription(MultiLangText description) {
		this.description = description;
	}

	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

}
