package com.heaptrip.domain.entity;

public class CommentAuthor extends BaseObject {

	private String name;

	private Image image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
