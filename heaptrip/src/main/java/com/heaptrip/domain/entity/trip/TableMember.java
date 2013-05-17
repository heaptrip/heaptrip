package com.heaptrip.domain.entity.trip;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class TableMember extends BaseObject {

	public static final String COLLECTION_NAME = "members";

	private String _class;

	private String tripId;

	private String tableId;

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}
