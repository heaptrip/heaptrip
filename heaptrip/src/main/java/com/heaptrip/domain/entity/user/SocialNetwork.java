package com.heaptrip.domain.entity.user;

import com.heaptrip.domain.entity.BaseObject;

public class SocialNetwork extends BaseObject {

	private String uid;
	
	private Boolean useImage;

	public SocialNetwork(SocialNetworkEnum name, String uid, Boolean useImage) {
		super();
		this.id = name.toString();
		this.uid = uid;
		this.useImage = useImage;
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

	public Boolean getUseImage() {
		return useImage;
	}

	public void setUseImage(Boolean useImage) {
		this.useImage = useImage;
	}
}
