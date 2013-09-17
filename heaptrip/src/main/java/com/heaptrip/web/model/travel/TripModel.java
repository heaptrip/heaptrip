package com.heaptrip.web.model.travel;

import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.RatingModel;

public class TripModel extends ContentModel {

	private Double price;

	
	private DateModel begin;
	private DateModel end;
	


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
