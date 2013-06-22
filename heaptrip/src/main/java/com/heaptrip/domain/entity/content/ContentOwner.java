package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.account.AccountEnum;

/**
 * 
 * The content owner
 * 
 */
public class ContentOwner extends BaseObject {

	// account type
	private AccountEnum type;

	// the owner's name
	private String name;

	// rating owner
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
