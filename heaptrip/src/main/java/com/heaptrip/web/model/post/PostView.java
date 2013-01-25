package com.heaptrip.web.model.post;

import java.util.Date;
import java.util.List;

import com.heaptrip.web.model.tiding.TidingView;

public class PostView implements TidingView {
	private String id;
	private String name;
	private Date dateCreate;
	private String description;

	private List<PostImage> images;

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

	public List<PostImage> getImages() {
		return images;
	}

	public void setImages(List<PostImage> images) {
		this.images = images;
	}

}
