package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Profile;

public class CommunityProfile extends Profile {

	private String skype;
	
	public CommunityProfile() {
		super("1");
	}

	public CommunityProfile(String id) {
		super(id);
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}
}
