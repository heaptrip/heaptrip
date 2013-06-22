package com.heaptrip.domain.entity.comment;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.image.Image;

/**
 * 
 * CommentAuthor
 * 
 */
public class CommentAuthor extends BaseObject {

	// user name
	private String name;

	// user avatar
	private Image image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
