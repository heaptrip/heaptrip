package com.heaptrip.domain.entity.trip;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

/**
 * 
 * Base entity for table users and table invites
 * 
 */
@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class TableMember extends BaseObject {

	public static final String COLLECTION_NAME = "members";

	// entity class
	private String _class;

	// trip id
	private String tripId;

	// table item id
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
