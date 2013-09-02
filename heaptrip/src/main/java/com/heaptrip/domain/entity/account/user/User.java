package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;

public class User extends Account {

	private String[] roles;
	
	private SocialNetwork[] net;
	
	private String extImageStore;
	
	private Byte[] imageCRC;
	
	private String[] friends;
	
	private String[] publishers;
	
	public User() {
		super();
		setTypeAccount(AccountEnum.USER);
		setFriends(new String[0]);
		setPublishers(new String[0]);
		setProfile(new UserProfile());
		setSetting(new UserSetting());
	}
	
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

	public String[] getPublishers() {
		return publishers;
	}

	public void setPublishers(String[] publishers) {
		this.publishers = publishers;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}
}
