package com.heaptrip.domain.entity;

/**
 * 
 * Favorite content
 * 
 */
public class FavoriteContent extends BaseObject {

	public static final String COLLECTION_NAME = "favorites";

	// content id
	private String contentId;

	// type of content
	private ContentEnum type;

	// user id
	private String userId;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public ContentEnum getType() {
		return type;
	}

	public void setType(ContentEnum type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
