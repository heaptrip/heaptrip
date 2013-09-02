package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Account;

public class Community extends Account {

	private String[] owners;
	
	private String[] staff;
	
	private String[] publishers;

	public Community() {
		super();
		owners = new String[0];
		setPublishers(new String[0]);
		staff = new String[0];
	}
	
	public String[] getOwners() {
		return owners;
	}

	public void setOwners(String[] owners) {
		this.owners = owners;
	}

	public String[] getStaff() {
		return staff;
	}

	public void setStaff(String[] staff) {
		this.staff = staff;
	}

	public String[] getPublishers() {
		return publishers;
	}

	public void setPublishers(String[] publishers) {
		this.publishers = publishers;
	}

}
