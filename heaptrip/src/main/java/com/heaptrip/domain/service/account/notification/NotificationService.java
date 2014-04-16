package com.heaptrip.domain.service.account.notification;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
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
     * find notifications by account notification criteria
     *
     * @param criteria notification criteria
     * @return list of notifications
     */
    List<Notification> findByUserNotificationCriteria(AccountNotificationCriteria criteria);

    /**
     * Get count of notifications by account notification criteria
     *
     * @param criteria notification criteria
     * @return count of notifications
     */
    long countByUserNotificationCriteria(AccountNotificationCriteria criteria);

    /**
     * find notifications by community notification criteria
     *
     * @param criteria notification criteria
     * @return list of notifications
     */
    List<Notification> findByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    /**
     * Get count of notifications by community notification criteria
     *
     * @param criteria notification criteria
     * @return count of notifications
     */
    long countByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    /**
     * Change notification status
     *
     * @param notificationId notification id
     * @param status         notification status
     */
    void changeStatus(String notificationId, NotificationStatusEnum status);
}
