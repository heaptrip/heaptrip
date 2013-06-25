package com.heaptrip.domain.entity.album;

import com.heaptrip.domain.entity.image.Image;

public class AlbumImage extends Image {

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