package com.heaptrip.web.model.travel;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.DateModel;

public class TripModel extends ContentModel {

	private Double price;
	private Double rating;
	private DateModel begin;
	private DateModel end;
	

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
