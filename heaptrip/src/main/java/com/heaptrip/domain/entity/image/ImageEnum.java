package com.heaptrip.domain.entity.image;

/**
 * 
 * List of possible images
 * 
 */
public enum ImageEnum {

	USER_PHOTO(55, 55),
	USER_PHOTO_PROFILE(300, 200),
	CONTENT_TITLE_IMAGE(300, 208),
	TRIP_ALBUM_SMALL_IMAGE(150, 150),
	TRIP_ALBUM_MEDIUM_IMAGE(611, 375),
	TRIP_ALBUM_FULL_IMAGE(1920, 1080),
	TRIP_ROUTE_SMALL_IMAGE(199, 184),
	TRIP_ROUTE_MEDIUM_IMAGE(597, 552),
	TRIP_ROUTE_FULL_IMAGE(1920, 1080);

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
