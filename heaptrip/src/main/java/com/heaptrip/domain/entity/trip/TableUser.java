package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.BaseDocument;

public class TableUser extends BaseDocument {

	private String name;

	private Boolean isOrganizer;

	private TableUserStatusEnum status;

	private TableUserPhoto photo;

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

	public TableUserPhoto getPhoto() {
		return photo;
	}

	public void setPhoto(TableUserPhoto photo) {
		this.photo = photo;
	}

}
