package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.image.Image;

/**
 * 
 * Trip user
 * 
 */
public class TripUser extends TripMember {

	// user id
	private String userId;

	// user name
	private String name;

	// a sign that the user is the organizer
	private Boolean isOrganizer;

	// user status
	private TableUserStatusEnum status;

	// user image
	private Image image;

	public TripUser() {
		super();
	}

	public TripUser(String id) {
		super();
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsOrganizer() {
		return isOrganizer;
	}

	public void setIsOrganizer(Boolean isOrganizer) {
		this.isOrganizer = isOrganizer;
	}

	public TableUserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TableUserStatusEnum status) {
		this.status = status;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
