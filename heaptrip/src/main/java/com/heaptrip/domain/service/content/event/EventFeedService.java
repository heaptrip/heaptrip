package com.heaptrip.domain.service.content.event;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.domain.service.content.feed.FeedService;

/**
 * Service to find events for feed
 */
public interface EventFeedService extends
		FeedService<Event, EventFeedCriteria, EventMyAccountCriteria, EventForeignAccountCriteria> {

}
