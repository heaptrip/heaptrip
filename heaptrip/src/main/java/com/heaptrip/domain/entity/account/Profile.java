package com.heaptrip.domain.entity.account;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.region.SimpleRegion;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Profile extends BaseObject {
	
	private SimpleRegion location;
	
	private String[] langs;
	
	private String desc;

	private SimpleCategory[] categories;
	
	private SimpleRegion[] regions;
	
	public Profile() {
		super();
	}

	public Profile(String id) {
		super();
		this.id = id;
	}

	public SimpleRegion getLocation() {
		return location;
	}

	public void setLocation(SimpleRegion location) {
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

	public SimpleCategory[] getCategories() {
		return categories;
	}

	public void setCategories(SimpleCategory[] categories) {
		this.categories = categories;
	}

	public SimpleRegion[] getRegions() {
		return regions;
	}

	public void setRegions(SimpleRegion[] regions) {
		this.regions = regions;
	}
}
