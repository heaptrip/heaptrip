package com.heaptrip.web.model.travel;

import java.util.Date;

import com.heaptrip.web.model.content.ContentModel;

public class TripModel extends ContentModel {

	private Double price;
	private Double rating;
	private Date begin;
	private Date end;

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

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
