package com.heaptrip.domain.entity.post;

import java.util.Date;

import com.heaptrip.domain.entity.BaseEntity;

public class PostEntity extends BaseEntity {
	public static final String COLLECTION_NAME = "posts";

	private String id;
	private String name;
	private Date dateCreate;
	private String photoUrl;
	private String smallPhotoUrl;
	private String description;

	@SuppressWarnings("unchecked")
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}

	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
