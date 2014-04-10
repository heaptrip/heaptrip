package com.heaptrip.domain.service.account.criteria.notification;

import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;

import java.util.List;

/**
 * Criteria to search communities notifications by owner id
 */
public class CommunityNotificationCriteria extends AbstractNotificationCriteria {

    private String userId;

    private String[] toIds; // мои сообщества

    private List<NotificationTypeEnum> notificationTypes;

    public String[] getToIds() {
        return toIds;
    }

    public void setToIds(String[] toIds) {
        this.toIds = toIds;
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
