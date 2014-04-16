package com.heaptrip.domain.service.account.criteria.notification;

import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;

import java.util.List;

/**
 * Criteria to search communities notifications by owner id
 */
public class CommunityNotificationCriteria extends AbstractNotificationCriteria {

    // who are available
    private String userId;

    // community id, which will receive notification
    private String communityId;

    private List<NotificationTypeEnum> notificationTypes;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<NotificationTypeEnum> getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(List<NotificationTypeEnum> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }
}
