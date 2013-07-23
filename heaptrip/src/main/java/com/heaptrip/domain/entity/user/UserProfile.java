package com.heaptrip.domain.entity.user;

import java.util.Date;

import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentRegion;

public class UserProfile extends User {

	private Date birthday;
	
	private ContentRegion location;
	
	private String[] langs;
	
	private String desc;
	
	private Knowledge[] knowledgies;
	
	private Practice[] practices;
	
	private ContentCategory[] categories;
	
	private ContentRegion[] regions;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Knowledge[] getKnowledgies() {
		return knowledgies;
	}

	public void setKnowledgies(Knowledge[] knowledgies) {
		this.knowledgies = knowledgies;
	}

	public Practice[] getPractices() {
		return practices;
	}

	public void setPractices(Practice[] practices) {
		this.practices = practices;
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
