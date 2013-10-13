package com.heaptrip.web.model.content;

public class ContentRatingModel extends RatingStarsModel {

	private String contentId;
	private String contentType;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
