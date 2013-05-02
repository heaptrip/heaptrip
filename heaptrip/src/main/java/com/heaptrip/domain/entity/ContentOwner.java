package com.heaptrip.domain.entity;

public class ContentOwner extends BaseObject {

	private AccountEnum type;

	private String name;

	private Double rating;

	public ContentOwner() {
		super();
	}

	public ContentOwner(String id) {
		super();
		this.id = id;
	}

	public AccountEnum getType() {
		return type;
	}

	public void setType(AccountEnum type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
