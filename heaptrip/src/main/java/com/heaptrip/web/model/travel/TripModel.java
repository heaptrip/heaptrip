package com.heaptrip.web.model.travel;

import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.RatingModel;

public class TripModel extends ContentModel {

	private Double price;
	private RatingModel rating;
	
	private DateModel begin;
	private DateModel end;
	

	public RatingModel getRating() {
		return rating;
	}

	public void setRating(RatingModel rating) {
		this.rating = rating;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public DateModel getBegin() {
		return begin;
	}

	public void setBegin(DateModel begin) {
		this.begin = begin;
	}

	public DateModel getEnd() {
		return end;
	}

	public void setEnd(DateModel end) {
		this.end = end;
	}

	

}
