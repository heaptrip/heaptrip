package com.heaptrip.domain.entity.album;

/**
 * 
 * References to images in GridFS
 * 
 */
public class ImageReferences {

	// _id of small image
	private String small;

	// _id of medium image
	private String medium;

	// _id of full image
	private String full;

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

}
