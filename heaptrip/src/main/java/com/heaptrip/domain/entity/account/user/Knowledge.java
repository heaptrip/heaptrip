package com.heaptrip.domain.entity.account.user;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;

public class Knowledge extends BaseObject {
	
	// begin date
	private Date begin;

	// end date
	private Date end;

	private String specialist;

	private String location;
	
	private String document;

	public Date getBegin() {
		return begin;
	}
	
	public Knowledge() {
		super();
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

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}	
}
