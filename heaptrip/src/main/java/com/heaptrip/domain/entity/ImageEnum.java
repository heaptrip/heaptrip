package com.heaptrip.domain.entity;

/**
 * 
 * List of possible images
 * 
 */
public enum ImageEnum {

	TRIP_IMAGE("id", "300", "208");

	// field name
	private String field;

	// image width
	private String width;

	// image height
	private String height;

	private ImageEnum(String field, String width, String height) {
		this.field = field;
		this.width = width;
		this.height = height;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
