package com.heaptrip.domain.service.account.notification;

import java.util.List;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;

public interface NotificationService {

	/**
	 * add notification
	 * 
	 * @param notification
	 */
	void addNotification(Notification notification);
	
	/**
	 * Get limit notifications
	 * 
	 * @param criteria
	 * @return list of notifications
	 */
	List<Notification> getNotifications(NotificationCriteria criteria);
	
	/**
	 * change status notification
	 * 
	 * @param notificationId
	 * @param status
	 */
	void changeStatus(String notificationId, NotificationStatusEnum status);
}
