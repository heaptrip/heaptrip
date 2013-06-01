package com.heaptrip.domain.entity;

import java.util.Date;
import java.util.HashMap;

/**
 * 
 * Image
 * 
 */
public class Image extends HashMap<String, String> {

	private static final long serialVersionUID = 3000727658806127076L;

	// image name
	private String name;

	// image text
	private String text;

	// date uploaded
	private Date uploaded;

	/**
	 * Get image id
	 * 
	 * @param imageType
	 *            type of image
	 * @return image id
	 */
	public String getImageId(ImageEnum imageType) {
		return get(imageType.getField());
	}

	/**
	 * Add image id
	 * 
	 * @param imageId
	 *            image id
	 * @param imageType
	 *            type of image
	 * 
	 */
	public void addImageId(String imageId, ImageEnum imageType) {
		put(imageType.getField(), imageId);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
