package com.heaptrip.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Setting extends BaseObject {

	public Setting() {
		super();
	}

	public Setting(String id) {
		super();
		this.id = id;
	}
}
