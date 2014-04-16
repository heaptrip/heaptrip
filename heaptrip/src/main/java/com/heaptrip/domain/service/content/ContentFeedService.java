package com.heaptrip.domain.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

/**
 * Service to find contents for feed
 */
public interface ContentFeedService extends
        FeedService<Content, FeedCriteria, MyAccountCriteria, ForeignAccountCriteria> {

}
