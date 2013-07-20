package com.heaptrip.domain.entity.user;

import com.heaptrip.domain.entity.BaseObject;

public class SocialNetwork extends BaseObject {

	private String uid;

	public SocialNetwork(SocialNetworkEnum name, String uid) {
		super();
		this.id = name.toString();
		this.uid = uid;
	}

	public SocialNetwork() {
		super();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
