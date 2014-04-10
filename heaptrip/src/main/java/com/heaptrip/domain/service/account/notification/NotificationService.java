package com.heaptrip.domain.service.account.notification;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;

import java.util.List;

public interface NotificationService {

    /**
     * Add notification
     *
     * @param notification notification
     */
    Notification addNotification(Notification notification);

    /**
     * find notifications by notification criteria
     *
     * @param criteria notification criteria
     * @return list of notifications
     */
    List<Notification> findByNotificationCriteria(NotificationCriteria criteria);

    /**
     * Get count of notifications by notification criteria
     *
     * @param criteria notification criteria
     * @return count of notifications
     */
    long countByNotificationCriteria(NotificationCriteria criteria);

    /**
     * Change notification status
     *
     * @param notificationId notification id
     * @param status         notification status
     */
    void changeStatus(String notificationId, NotificationStatusEnum status);
}
