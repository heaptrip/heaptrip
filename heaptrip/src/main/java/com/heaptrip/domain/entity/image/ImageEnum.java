package com.heaptrip.domain.entity.image;

/**
 * 
 * List of possible images
 * 
 */
public enum ImageEnum {

	CONTENT_TITLE_IMAGE(300, 208);

	// image width
	private int width;

	// image height
	private int height;

	private ImageEnum(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
