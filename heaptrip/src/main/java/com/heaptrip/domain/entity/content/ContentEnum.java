package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.content.trip.Trip;

/**
 * 
 * Enumeration of the types of content
 * 
 */
public enum ContentEnum {

	TRIP(Trip.class.getName()), POST(Post.class.getName()), QA("null"), EVENT("null");

	private String clazz;

	private ContentEnum(String clazz) {
		this.clazz = clazz;
	}

	public String getClazz() {
		return clazz;
	}

}
