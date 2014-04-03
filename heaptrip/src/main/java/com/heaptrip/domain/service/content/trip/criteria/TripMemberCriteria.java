package com.heaptrip.domain.service.content.trip.criteria;

import com.heaptrip.domain.service.criteria.Criteria;

/**
 * Base criteria for search trip members
 */
public class TripMemberCriteria extends Criteria {

    // trip id
    private String tripId;

    // table id
    private String tableId;

    // user id, need to determine the list of possible actions in the schedule of trip.
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
