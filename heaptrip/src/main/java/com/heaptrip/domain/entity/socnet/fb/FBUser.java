package com.heaptrip.domain.entity.socnet.fb;

import java.sql.Date;

public class FBUser {

	private String id;
	private String name;
	private String first_name;
	private String last_name;
	private String link;
	private String username;
	private FBUserHomeTown hometown;
	private String gender;
	private Short timezone;
	private String locale;
	private Boolean verified;
	private Date updated_time;
	private String picture;
	private String picture_large;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public FBUserHomeTown getHometown() {
		return hometown;
	}

	public void setHometown(FBUserHomeTown hometown) {
		this.hometown = hometown;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Short getTimezone() {
		return timezone;
	}

	public void setTimezone(Short timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Date getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture_large() {
		return picture_large;
	}

	public void setPicture_large(String picture_large) {
		this.picture_large = picture_large;
	}

}
