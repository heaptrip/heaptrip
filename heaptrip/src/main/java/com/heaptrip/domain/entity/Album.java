package com.heaptrip.domain.entity;

import java.util.Date;

public class Album extends BaseObject {

	private String targetId;

	private Date created;

	private AlbumPhoto[] photos;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
