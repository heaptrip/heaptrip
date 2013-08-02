package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.trip.Trip;

/**
 * 
 * Enumeration of the types of content
 * 
 */
public enum ContentEnum {

	TRIP(Trip.class.getName()), 
	POST("null"), 
	QA("null"), 
	EVENT("null");

	private String clazz;

	private ContentEnum(String clazz) {
		this.clazz = clazz;
	}

	public String getClazz() {
		return clazz;
	}
}
