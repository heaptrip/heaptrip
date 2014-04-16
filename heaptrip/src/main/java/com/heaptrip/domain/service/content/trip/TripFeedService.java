package com.heaptrip.domain.service.content.trip;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.FeedService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;

/**
 * Service to find trips for feed
 */
public interface TripFeedService extends
        FeedService<Trip, TripFeedCriteria, TripMyAccountCriteria, TripForeignAccountCriteria> {

}
