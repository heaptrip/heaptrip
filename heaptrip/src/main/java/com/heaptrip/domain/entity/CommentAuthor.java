package com.heaptrip.domain.entity;

public class CommentAuthor extends BaseObject {

	private String name;

	private Photo photo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
}
