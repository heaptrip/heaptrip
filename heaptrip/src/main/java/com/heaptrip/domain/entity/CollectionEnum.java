package com.heaptrip.domain.entity;

/**
 * 
 * Enumeration of all the collections in the database
 * 
 */
public enum CollectionEnum {
	CONTENTS("contents"), 
	ACCOUNTS("accounts"), 
	COMMENTS("comments"), 
	CATEGORIES("categories"), 
	REGIONS("regions"), 
	JOURNAL("journal"), 
	MEMBERS("members"), 
	IMAGES("images"),
	RATINGS("ratings");

	private String name;

	private CollectionEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
