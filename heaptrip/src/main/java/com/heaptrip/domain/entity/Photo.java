package com.heaptrip.domain.entity;

import java.util.Date;

/**
 * 
 * Photo
 * 
 */
public class Photo extends BaseObject {

	// small photo id
	protected String smallId;

	// name photos
	private String name;

	// date uploaded
	private Date uploaded;

	public String getSmallId() {
		return smallId;
	}

	public void setSmallId(String smallId) {
		this.smallId = smallId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUploaded() {
		return uploaded;
	}

	public void setUploaded(Date uploaded) {
		this.uploaded = uploaded;
	}
}
