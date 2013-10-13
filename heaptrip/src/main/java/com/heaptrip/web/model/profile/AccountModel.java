package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.StatusModel;

public class AccountModel {

	private String name;

	private String email;

	private String image;

	private String imageContentId;

	private ProfileModel profile;

	private StatusModel status;

	private RatingModel rating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageContentId() {
		return imageContentId;
	}

	public void setImageContentId(String imageContentId) {
		this.imageContentId = imageContentId;
	}

	public ProfileModel getProfile() {
		return profile;
	}

	public void setProfile(ProfileModel profile) {
		this.profile = profile;
	}

	public StatusModel getStatus() {
		return status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

	public RatingModel getRating() {
		return rating;
	}

	public void setRating(RatingModel rating) {
		this.rating = rating;
	}

}
