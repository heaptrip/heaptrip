package com.heaptrip.domain.entity.rating;

/**
 * 
 * Stores information about the rating of content
 * 
 */
public class ContentRating extends TotalRating {

	public static ContentRating getDefaultValue() {
		ContentRating contentRating = new ContentRating();
		contentRating.setCount(0);
		contentRating.setValue(0.25);
		return contentRating;
	}
}
