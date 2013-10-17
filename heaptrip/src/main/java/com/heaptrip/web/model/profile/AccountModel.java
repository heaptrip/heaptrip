package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.RatingModel;

public class AccountModel {

	private String id;
	private String name;
	private RatingModel rating;
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

	public RatingModel getRating() {
		return rating;
	}

	public void setRating(RatingModel rating) {
		this.rating = rating;
	}

	public ImageModel getImage() {
		return image;
	}

	public void setImage(ImageModel image) {
		this.image = image;
	}

}
