package com.heaptrip.domain.service.account.notification;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.service.account.criteria.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.CommunityNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;

import java.util.List;

public interface NotificationService {

    /**
     * Add notification
     *
     * @param notification notification
     */
    void addNotification(Notification notification);


    /**
     * Get notifications by common notification criteria
     *
     * @param criteria common notification criteria
     * @return list of notifications
     */
    List<Notification> getNotificationsByNotificationCriteria(NotificationCriteria criteria);

    /**
     * Get count of notifications by common notification criteria
     *
     * @param criteria common notification criteria
     * @return count of notifications
     */
    long getCountByNotificationCriteria(NotificationCriteria criteria);

    /**
     * Get notifications by c notification criteria
     *
     * @param criteria account notification criteria
     * @return list of notifications
     */
    List<Notification> getNotificationsByAccountNotificationCriteria(AccountNotificationCriteria criteria);


    /**
     * Get count of notifications by account notification criteria
     *
     * @param criteria account notification criteria
     * @return count of notifications
     */
    long getCountByAccountNotificationCriteria(AccountNotificationCriteria criteria);

    /**
     * Get notifications by community notification criteria
     *
     * @param criteria community notification criteria
     * @return list of notifications
     */
    List<Notification> getNotificationsByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    /**
     * Get count of notifications by community notification criteria
     *
     * @param criteria community notification criteria
     * @return count of notifications
     */
    long getCountByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    /**
     * Change notification status
     *
     * @param notificationId notification id
     * @param status         notification status
     */
    void changeStatus(String notificationId, NotificationStatusEnum status);
}
