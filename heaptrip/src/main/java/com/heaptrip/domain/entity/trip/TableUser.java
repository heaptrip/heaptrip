package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.Photo;

/**
 * 
 * Table user
 * 
 */
public class TableUser extends TableMember {

	// userId
	private String userId;

	// user name
	private String name;

	// a sign that the user is the organizer
	private Boolean isOrganizer;

	// user status
	private TableUserStatusEnum status;

	// user photo
	private Photo photo;

	public TableUser() {
		super();
	}

	public TableUser(String id) {
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
