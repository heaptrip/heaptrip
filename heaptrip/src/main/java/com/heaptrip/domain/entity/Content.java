package com.heaptrip.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Content extends CollectionObject {

	private String _class;

	private ContentCategory[] categories;

	private ContentRegion[] regions;

	private ContentStatus status;

	private ContentName name;

	private Photo photo;

	private Date created;

	private Date deleted;

	private Long views;

	private LangEnum[] langs;

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public ContentCategory[] getCategories() {
		return categories;
	}

	public void setCategories(ContentCategory[] categories) {
		this.categories = categories;
	}

	public ContentRegion[] getRegions() {
		return regions;
	}

	public void setRegions(ContentRegion[] regions) {
		this.regions = regions;
	}

	public ContentStatus getStatus() {
		return status;
	}

	public void setStatus(ContentStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public ContentName getName() {
		return name;
	}

	public void setName(ContentName name) {
		this.name = name;
	}

	public LangEnum[] getLangs() {
		return langs;
	}

	public void setLangs(LangEnum[] langs) {
		this.langs = langs;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

}
