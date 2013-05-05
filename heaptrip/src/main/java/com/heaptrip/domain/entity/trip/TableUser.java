package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.Photo;

public class TableUser extends BaseObject {

	private String name;

	private Boolean isOrganizer;

	private TableUserStatusEnum status;

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

}
