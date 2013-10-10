package com.heaptrip.service.content.event;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.EventFeedService;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;

@Service
public class EventFeedServiceImpl implements EventFeedService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public List<Event> getContentsByFeedCriteria(EventFeedCriteria feedCriteria) {
		Assert.notNull(feedCriteria, "feedCriteria must not be null");
		Assert.notNull(feedCriteria.getContentType(), "contentType must not be null");
		return eventRepository.findByFeedCriteria(feedCriteria);
	}

	@Override
	public List<Event> getContentsByMyAccountCriteria(EventMyAccountCriteria myAccountCriteria) {
		Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
		Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(!myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
		return eventRepository.findByMyAccountCriteria(myAccountCriteria);
	}

	@Override
	public List<Event> getContentsByForeignAccountCriteria(EventForeignAccountCriteria foreignAccountCriteria) {
		Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(!foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
		return eventRepository.findByForeignAccountCriteria(foreignAccountCriteria);
	}

	@Override
	public long getCountByFeedCriteria(EventFeedCriteria feedCriteria) {
		Assert.notNull(feedCriteria, "feedCriteria must not be null");
		Assert.notNull(feedCriteria.getContentType(), "contentType must not be null");
		return eventRepository.getCountByFeedCriteria(feedCriteria);
	}

	@Override
	public long getCountByMyAccountCriteria(EventMyAccountCriteria myAccountCriteria) {
		Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
		Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(!myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
		return eventRepository.getCountByMyAccountCriteria(myAccountCriteria);
	}

	@Override
	public long getCountByForeignAccountCriteria(EventForeignAccountCriteria foreignAccountCriteria) {
		Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(!foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
		return eventRepository.getCountByForeignAccountCriteria(foreignAccountCriteria);
	}

}
