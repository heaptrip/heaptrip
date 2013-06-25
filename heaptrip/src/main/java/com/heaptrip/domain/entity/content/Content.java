package com.heaptrip.domain.entity.content;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.image.Image;

/**
 * 
 * Base entity for trips, posts, questions and events
 * 
 */
@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Content extends BaseObject {

	public static final String COLLECTION_NAME = "contents";

	// entity class name
	private String _class;

	// content owner
	private ContentOwner owner;

	// content categories
	private ContentCategory[] categories;

	// content regions
	private ContentRegion[] regions;

	// content status
	private ContentStatus status;

	// multilingual name of the content
	private MultiLangText name;

	// image
	private Image image;

	// date created
	private Date created;

	// date of remove
	private Date deleted;

	// list of user IDs that are allowed to view content. [0] - allow all
	private String[] allowed;

	// list owners. If the trip is owned by the company, then it may be a few
	// owners
	private String[] owners;

	// number of views
	private Long views;

	// language in which the trip was created
	private String mainlang;

	// set of languages ​​that are available for this content
	private String[] langs;

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public ContentOwner getOwner() {
		return owner;
	}

	public void setOwner(ContentOwner owner) {
		this.owner = owner;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public MultiLangText getName() {
		return name;
	}

	public void setName(MultiLangText name) {
		this.name = name;
	}

	public String[] getLangs() {
		return langs;
	}

	public void setLangs(String[] langs) {
		this.langs = langs;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public String[] getAllowed() {
		return allowed;
	}

	public void setAllowed(String[] allowed) {
		this.allowed = allowed;
	}

	public String[] getOwners() {
		return owners;
	}

	public void setOwners(String[] owners) {
		this.owners = owners;
	}

	public String getMainlang() {
		return mainlang;
	}

	public void setMainlang(String mainlang) {
		this.mainlang = mainlang;
	}
}