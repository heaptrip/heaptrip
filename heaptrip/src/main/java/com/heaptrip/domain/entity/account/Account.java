package com.heaptrip.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.rating.AccountRating;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Account extends BaseObject {

	public static final String COLLECTION_NAME = "accounts";

	private String _class;

	private String name;

	private String email;

    private AccountImageReferences images;

	private Profile profile;

	private Setting setting;

	private AccountStatusEnum status;

	private AccountEnum typeAccount;
	
	private AccountRating rating;

    public Account() {}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AccountStatusEnum status) {
		this.status = status;
	}

	public AccountEnum getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(AccountEnum typeAccount) {
		this.typeAccount = typeAccount;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public AccountRating getRating() {
		return rating;
	}

	public void setRating(AccountRating rating) {
		this.rating = rating;
	}

    public AccountImageReferences getImages() {
        return images;
    }

    public void setImages(AccountImageReferences images) {
        this.images = images;
    }
}
