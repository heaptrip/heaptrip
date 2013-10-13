package com.heaptrip.web.model.content;

public class RatingStarsModel extends RatingModel {

	private String stars;

	private Boolean locked;

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

}
