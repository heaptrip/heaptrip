package com.heaptrip.web.model.content;

public class ContentModel {

	private String id;
	private String name;
	private String image;
	private DateModel created;
	private ContentOwnerModel owner;
	private Long comments;
	private Long views;

	private CategoryModel[] categories;
	private RegionModel[] regions;
	private String[] langs;
	private StatusModel status;
	private String locale;

	private String summary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateModel getCreated() {
		return created;
	}

	public void setCreated(DateModel created) {
		this.created = created;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CategoryModel[] getCategories() {
		return categories;
	}

	public void setCategories(CategoryModel[] categories) {
		this.categories = categories;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public ContentOwnerModel getOwner() {
		return owner;
	}

	public void setOwner(ContentOwnerModel owner) {
		this.owner = owner;
	}

	public RegionModel[] getRegions() {
		return regions;
	}

	public void setRegions(RegionModel[] regions) {
		this.regions = regions;
	}

	public String[] getLangs() {
		return langs;
	}

	public void setLangs(String[] langs) {
		this.langs = langs;
	}

	public StatusModel getStatus() {
		return status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
