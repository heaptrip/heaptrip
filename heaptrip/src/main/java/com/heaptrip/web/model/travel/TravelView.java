package com.heaptrip.web.model.travel;

import java.util.Date;

import com.heaptrip.web.model.tiding.TidingView;

public class TravelView implements TidingView {

	private String name;
	private Date dateCreate;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getDateCreate() {
		return this.dateCreate;
	}

	@Override
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

}
