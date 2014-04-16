package com.heaptrip.domain.repository.account.notification;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification> {

    public List<Notification> findByNotificationCriteria(NotificationCriteria criteria);

    public long countByNotificationCriteria(NotificationCriteria criteria);

    public List<Notification> findByUserNotificationCriteria(AccountNotificationCriteria criteria);

    public long countByUserNotificationCriteria(AccountNotificationCriteria criteria);

    public List<Notification> findByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    public long countByCommunityNotificationCriteria(CommunityNotificationCriteria criteria);

    public void changeStatus(String notificationId, NotificationStatusEnum status);
}
