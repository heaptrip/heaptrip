package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.StatusModel;

public class AccountInfoModel extends AccountModel {

	private String name;

	private String email;

	private ProfileModel profile;

	private StatusModel status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ProfileModel getProfile() {
		return profile;
	}

	public void setProfile(ProfileModel profile) {
		this.profile = profile;
	}

	public StatusModel getStatus() {
		return status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

}
