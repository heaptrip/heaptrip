package com.heaptrip.web.model.travel;

import com.heaptrip.web.model.adm.User;

public class Travel extends TravelView {
	private User owner;
	private User[] users;

	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User[] getUsers() {
		return this.users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
}
