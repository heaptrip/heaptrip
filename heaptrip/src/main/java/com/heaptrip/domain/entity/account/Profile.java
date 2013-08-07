package com.heaptrip.domain.entity.account;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentRegion;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Profile extends BaseObject {
	
	private ContentRegion location;
	
	private String[] langs;
	
	private String desc;

	private ContentCategory[] categories;
	
	private ContentRegion[] regions;
	
	public Profile() {
		super();
	}

	public Profile(String id) {
		super();
		this.id = id;
	}

	public ContentRegion getLocation() {
		return location;
	}

	public void setLocation(ContentRegion location) {
		this.location = location;
	}

	public String[] getLangs() {
		return langs;
	}

	public void setLangs(String[] langs) {
		this.langs = langs;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
}
