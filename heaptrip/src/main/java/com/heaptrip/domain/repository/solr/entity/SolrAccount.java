package com.heaptrip.domain.repository.solr.entity;

public class SolrAccount extends SolrDocument {

	private String clazz;
	private String name;
	private String[] categories;
	private String[] regions;
	private String[] friends;
	private String[] subscribers;
	private String[] owners;
	private String[] staff;
	private String[] members;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String[] getRegions() {
		return regions;
	}

	public void setRegions(String[] regions) {
		this.regions = regions;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	public String[] getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(String[] subscribers) {
		this.subscribers = subscribers;
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

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

}
