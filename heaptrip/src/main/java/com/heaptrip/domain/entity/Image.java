package com.heaptrip.domain.entity;

import java.util.Date;

/**
 * 
 * Image
 * 
 */
public class Image extends BaseObject {

	// image name
	private String name;

	// image text (description)
	private String text;

	// date uploaded
	private Date uploaded;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getUploaded() {
		return uploaded;
	}

	public void setUploaded(Date uploaded) {
		this.uploaded = uploaded;
	}

}
