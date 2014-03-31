package com.heaptrip.domain.service.content.event.criteria;

import com.heaptrip.domain.service.criteria.Criteria;

/**
 * Criteria for search event members
 */
public class EventMemberCriteria extends Criteria {

    // event id
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
