package com.heaptrip.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class Account extends BaseObject {

	public static final String COLLECTION_NAME = "accounts";
	
	// entity class name
	private String _class;
	
	// name
	private String name;
	
	private String email;
	
	private String imageProfileId;
	
	private String imageContentId;
	
	private Profile profile;
	
	private Setting setting;

	// account status
	private AccountStatusEnum status;
	
	// type account 
	private AccountEnum typeAccount;

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

	public String getImageProfileId() {
		return imageProfileId;
	}

	public void setImageProfileId(String imageProfileId) {
		this.imageProfileId = imageProfileId;
	}

	public String getImageContentId() {
		return imageContentId;
	}

	public void setImageContentId(String imageContentId) {
		this.imageContentId = imageContentId;
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
}
