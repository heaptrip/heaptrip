package com.heaptrip.domain.entity.post;

import java.util.Date;
import java.util.List;


@Deprecated
public class PostEntity extends BaseEntity {
	public static final String COLLECTION_NAME = "posts";

	private String id;
	private String name;
	private Date dateCreate;
	private String description;
	private List<ImageEntity> images;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}
}