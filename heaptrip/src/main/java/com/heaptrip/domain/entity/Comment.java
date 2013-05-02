package com.heaptrip.domain.entity;

import java.util.Date;

public class Comment extends BaseObject {

	private String target;

	private String parent;

	private String slug;

	private String fullSlug;

	private Date created;

	private CommentAuthor author;

	private String text;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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
