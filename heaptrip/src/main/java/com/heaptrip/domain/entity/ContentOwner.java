package com.heaptrip.domain.entity;

public class ContentOwner extends BaseDocument {

	private AccountEnum type;

	private String name;

	private Double rating;

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
