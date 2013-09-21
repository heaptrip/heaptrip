package com.heaptrip.repository.account.notification;

import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.service.account.notification.NotificationServiceImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Repository
public class NotificationRepositoryImpl extends CrudRepositoryImpl<Notification> implements NotificationRepository {

	protected static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private QueryHelperFactory queryHelperFactory;

	@Override
	protected String getCollectionName() {
		return Notification.COLLECTION_NAME;
	}

	@Override
	protected Class<Notification> getCollectionClass() {
		return Notification.class;
	}

	@Override
	public List<Notification> getNotificationsByCriteria(NotificationCriteria criteria) {
		QueryHelper<NotificationCriteria> queryHelper = queryHelperFactory.getInstance(NotificationCriteria.class);

		MongoCollection coll = getCollection();
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria);
		String hint = queryHelper.getHint(criteria);
		String sort = queryHelper.getSort(criteria);

		int skip = (criteria.getSkip() != null) ? criteria.getSkip().intValue() : 0;
		int limit = (criteria.getLimit() != null) ? criteria.getLimit().intValue() : 0;

		Iterable<Notification> iter = coll.find(query, parameters).sort(sort).skip(skip).limit(limit).hint(hint)
				.as(Notification.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public void changeStatus(String notificationId, NotificationStatusEnum status) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'status': #}}";
		WriteResult wr = coll.update(query, notificationId).with(updateQuery, status);
		logger.debug("WriteResult for update account: {}", wr);
	}
}
