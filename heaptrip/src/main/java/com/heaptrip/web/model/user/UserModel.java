package com.heaptrip.web.model.user;

import com.heaptrip.web.model.content.ImageModel;

public class UserModel {

	private String id;
	private String name;
	private Double rating;
	private ImageModel image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public ImageModel getImage() {
		return image;
	}

	public void setImage(ImageModel image) {
		this.image = image;
	}

}
