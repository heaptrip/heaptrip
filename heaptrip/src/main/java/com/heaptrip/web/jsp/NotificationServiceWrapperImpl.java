package com.heaptrip.web.jsp;

import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.system.RequestScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceWrapperImpl implements NotificationServiceWrapper {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService requestScopeService;

    @Override
    public int getUnreadNotificationFromUsers() {
        AccountNotificationCriteria criteria = new AccountNotificationCriteria();
        criteria.setAccountId(requestScopeService.getCurrentUser().getId());
        criteria.setStatus(NotificationStatusEnum.NEW.toString());

        return (int) notificationService.countByUserNotificationCriteria(criteria);
    }

    @Override
    public int getUnreadNotificationFromCommunities() {
        CommunityNotificationCriteria criteria = new CommunityNotificationCriteria();
        criteria.setUserId(requestScopeService.getCurrentUser().getId());
        criteria.setStatus(NotificationStatusEnum.NEW.toString());

        return (int) notificationService.countByCommunityNotificationCriteria(criteria);
    }
}
