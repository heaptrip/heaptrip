package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;

public interface NotificationProcessor<T extends Notification> {

    public NotificationTypeEnum[] getSupportedTypes();

    public MultiLangText getNotificationText(T notification);

    public void accept(T notification);

    public void reject(T notification);
}
