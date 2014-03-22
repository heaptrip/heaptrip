package com.heaptrip.domain.service.content.event.criteria;

import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.criteria.IDListCriteria;

/**
 * Criteria for finding a list of events from the whole database
 */
public class EventFeedCriteria extends FeedCriteria {

    // criteria for event types
    private IDListCriteria types;

    public IDListCriteria getTypes() {
        return types;
    }

    public void setTypes(IDListCriteria types) {
        this.types = types;
    }

}
