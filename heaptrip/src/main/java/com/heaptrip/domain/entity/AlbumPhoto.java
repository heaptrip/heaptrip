package com.heaptrip.domain.entity;

import java.util.Date;

public class AlbumPhoto extends Photo {

	private String name;

	private String text;

	private Date created;

	private AlbumPhotoOwner owner;

	private Long likes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public AlbumPhotoOwner getOwner() {
		return owner;
	}

	public void setOwner(AlbumPhotoOwner owner) {
		this.owner = owner;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}
}
