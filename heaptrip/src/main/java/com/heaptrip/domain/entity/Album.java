package com.heaptrip.domain.entity;

import java.util.Date;

// TODO
public class Album extends BaseObject {

	private String target;

	private Date created;

	private AlbumPhoto[] photos;

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

	public AlbumPhoto[] getPhotos() {
		return photos;
	}

	public void setPhotos(AlbumPhoto[] photos) {
		this.photos = photos;
	}
}
