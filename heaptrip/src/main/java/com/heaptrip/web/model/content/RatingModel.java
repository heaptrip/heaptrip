package com.heaptrip.web.model.content;

public class RatingModel {

	private Double value;
	private Integer stars;

	public RatingModel(Double value) {
		setValue(value);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
		// TODO: подсчитать
		this.stars = 2;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
}
