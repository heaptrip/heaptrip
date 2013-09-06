package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;

public class User extends Account {

	private String[] roles;
	
	private SocialNetwork[] net;
	
	private String extImageStore;
	
	private Byte[] imageCRC;
	
	private String[] friend;
	
	private String[] publisher;
	
	private String[] owner;
	
	private String[] employee;
	
	private String[] member;
	
	public User() {
		super();
		setTypeAccount(AccountEnum.USER);
		setStatus(AccountStatusEnum.NOTCONFIRMED);
		
		setFriend(new String[0]);
		setPublisher(new String[0]);
		setOwner(new String[0]);
		setEmployee(new String[0]);
		setMember(new String[0]);
		
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

	public String[] getFriend() {
		return friend;
	}

	public void setFriend(String[] friend) {
		this.friend = friend;
	}

	public String[] getPublisher() {
		return publisher;
	}

	public void setPublisher(String[] publisher) {
		this.publisher = publisher;
	}

	public String[] getOwner() {
		return owner;
	}

	public void setOwner(String[] owner) {
		this.owner = owner;
	}

	public String[] getMember() {
		return member;
	}

	public void setMember(String[] member) {
		this.member = member;
	}

	public String[] getEmployee() {
		return employee;
	}

	public void setEmployee(String[] employee) {
		this.employee = employee;
	}
}
