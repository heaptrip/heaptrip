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
	
	// TODO: обсудить и подумать
	public void setValue(Double value) {
		
		this.value = value;
		
		if (value < 0.2)
			stars = 0;
		else if (value < 0.4)
			stars = 1;
		else if (value < 0.6)
			stars = 2;
		else if (value < 0.8)
			stars = 3;
		else if (value < 1)
			stars = 4;
		else
			stars = 5;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
}
