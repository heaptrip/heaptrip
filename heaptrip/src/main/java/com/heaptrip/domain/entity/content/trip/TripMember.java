package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.content.Member;

/**
 * Base entity for trip users and trip invites
 */
public abstract class TripMember extends Member {

    // table item id
    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

}
