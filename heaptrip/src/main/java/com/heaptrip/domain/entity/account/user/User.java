package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;

public class User extends Account {

	private String[] roles;
	
	private SocialNetwork[] net;
	
	private String extImageStore;
	
	private Byte[] imageCRC;
	
	private String[] frends;
	
	private String[] signed;
	
	public User() {
		super();
		setTypeAccount(AccountEnum.USER);
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

	public String[] getFrends() {
		return frends;
	}

	public void setFrends(String[] frends) {
		this.frends = frends;
	}

	public String[] getSigned() {
		return signed;
	}

	public void setSigned(String[] signed) {
		this.signed = signed;
	}
}
