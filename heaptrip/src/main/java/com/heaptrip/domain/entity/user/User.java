package com.heaptrip.domain.entity.user;

import com.heaptrip.domain.entity.account.Account;

public class User extends Account {

	private String[] roles;
	
	private SocialNetwork[] net;
	
	private String extImageStore;
	
	private Byte[] imageCRC;
	
	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	public SocialNetwork[] getNet() {
		return net;
	}

	public void setNet(SocialNetwork[] net) {
		this.net = net;
	}
	
	public Byte[] getImageCRC() {
		return imageCRC;
	}

	public void setImageCRC(Byte[] imageCRC) {
		this.imageCRC = imageCRC;
	}

	public String getExternalImageStore() {
		return extImageStore;
	}

	public void setExtImageStore(String extImageStore) {
		this.extImageStore = extImageStore;
	}
}
