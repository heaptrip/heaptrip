package com.heaptrip.domain.entity;

public class AlbumImage extends Image {

	private static final long serialVersionUID = -3263134567529035230L;

	private AlbumImageOwner owner;

	private Long likes;

	public AlbumImageOwner getOwner() {
		return owner;
	}

	public void setOwner(AlbumImageOwner owner) {
		this.owner = owner;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}
}
