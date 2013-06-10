package com.heaptrip.web.model.travel;

import com.heaptrip.web.model.content.ContentModel;

public class TripModel extends ContentModel {

	private Double price;
	private Double rating;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
