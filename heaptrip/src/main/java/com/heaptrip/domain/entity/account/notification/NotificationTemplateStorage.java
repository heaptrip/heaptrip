package com.heaptrip.domain.entity.account.notification;


import java.util.Map;

public class NotificationTemplateStorage {

    private Map<NotificationTypeEnum, NotificationTemplate> notificationTemplates;

    public Map<NotificationTypeEnum, NotificationTemplate> getNotificationTemplates() {
        return notificationTemplates;
    }

    public void setNotificationTemplates(Map<NotificationTypeEnum, NotificationTemplate> notificationTemplates) {
        this.notificationTemplates = notificationTemplates;
    }
}
