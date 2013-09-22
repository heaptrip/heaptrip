package com.heaptrip.domain.service.content.trip;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.feed.FeedService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;

public interface TripFeedService extends
		FeedService<Trip, TripFeedCriteria, TripMyAccountCriteria, TripForeignAccountCriteria> {

}
