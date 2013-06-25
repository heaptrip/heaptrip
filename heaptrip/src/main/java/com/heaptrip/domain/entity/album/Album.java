package com.heaptrip.domain.entity.album;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;

// TODO
public class Album extends BaseObject {

	private String target;

	private Date created;

	private AlbumImage[] images;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public AlbumImage[] getImages() {
		return images;
	}

	public void setImages(AlbumImage[] images) {
		this.images = images;
	}

}