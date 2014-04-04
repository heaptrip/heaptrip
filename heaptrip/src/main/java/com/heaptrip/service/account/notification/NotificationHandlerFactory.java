package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.util.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationHandlerFactory {

    private Map<NotificationTypeEnum, NotificationHandler> notificationHandlers = new HashMap<>();

    @Autowired
    private SpringApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, NotificationHandler> handlers = applicationContext.getBeansOfType(NotificationHandler.class);
        for (String beanName : handlers.keySet()) {
            NotificationHandler handler = handlers.get(beanName);
            if (handler.getSupportedTypes() != null) {
                for (NotificationTypeEnum type : handler.getSupportedTypes()) {
                    Assert.isTrue(!notificationHandlers.containsKey(type), "Duplicate notification handler for notification type: " + type);
                    notificationHandlers.put(type, handler);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Notification> NotificationHandler<T> getNotificationHandler(NotificationTypeEnum notificationType) {
        return notificationHandlers.get(notificationType);
    }

}
