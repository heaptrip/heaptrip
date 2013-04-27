package com.heaptrip.domain.entity;

import java.util.Date;

public class Comment extends BaseObject {

	private String targetId;

	private String parentId;

	private String slug;

	private String fullSlug;

	private Date created;

	private CommentAuthor author;

	private String text;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getFullSlug() {
		return fullSlug;
	}

	public void setFullSlug(String fullSlug) {
		this.fullSlug = fullSlug;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public CommentAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CommentAuthor author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
