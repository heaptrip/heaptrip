package com.heaptrip.domain.entity.account.user;

import java.util.Date;

import com.heaptrip.domain.entity.account.Profile;

public class UserProfile extends Profile {

	private Date birthday;
	
	private Knowledge[] knowledgies;
	
	private Practice[] practices;
	
	public UserProfile() {
		super("1");
	}

	public UserProfile(String id) {
		super(id);
	}
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
}