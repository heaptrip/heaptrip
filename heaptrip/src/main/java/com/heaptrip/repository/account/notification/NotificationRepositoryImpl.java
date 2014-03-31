package com.heaptrip.repository.account.notification;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.service.account.notification.NotificationServiceImpl;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepositoryImpl extends CrudRepositoryImpl<Notification> implements NotificationRepository {

	protected static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private QueryHelperFactory queryHelperFactory;

	@Override
	protected String getCollectionName() {
        return CollectionEnum.NOTIFICATIONS.getName();
	}

	@Override
	protected Class<Notification> getCollectionClass() {
		return Notification.class;
	}

	@Override
	public List<Notification> getNotificationsByCriteria(NotificationCriteria criteria) {
		QueryHelper<NotificationCriteria, Notification> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria);
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
