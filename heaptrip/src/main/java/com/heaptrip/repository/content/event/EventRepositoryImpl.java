package com.heaptrip.repository.content.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.repository.content.FeedRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;

@Service
public class EventRepositoryImpl extends FeedRepositoryImpl<Event> implements EventRepository {

	@Autowired
	private QueryHelperFactory queryHelperFactory;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	public List<Event> findByFeedCriteria(EventFeedCriteria criteria) {
		QueryHelper<EventFeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(EventFeedCriteria.class);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Event> findByMyAccountCriteria(EventMyAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public List<Event> findByForeignAccountCriteria(EventForeignAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public long getCountByFeedCriteria(EventFeedCriteria criteria) {
		QueryHelper<FeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(FeedCriteria.class);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMyAccountCriteria(EventMyAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public long getCountByForeignAccountCriteria(EventForeignAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	protected Class<Event> getCollectionClass() {
		return Event.class;
	}

	@Override
	protected String getCollectionName() {
		return CollectionEnum.CONTENTS.getName();
	}

}
