package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.content.Member;

/**
 * 
 * Base entity for table users and table invites
 * 
 */
public abstract class TableMember extends Member {

	// table item id
	private String tableId;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}
