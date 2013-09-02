package com.heaptrip.domain.repository.account.notification;

import java.util.List;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;

public interface NotificationRepository extends CrudRepository<Notification> {
	
	public List<Notification> getNotificationsByCriteria(NotificationCriteria criteria);
	
	public void changeStatus(String notificationId, NotificationStatusEnum status);
}
