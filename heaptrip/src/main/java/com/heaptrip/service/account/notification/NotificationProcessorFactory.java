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
public class NotificationProcessorFactory {

    private Map<NotificationTypeEnum, NotificationProcessor> notificationProcessors = new HashMap<>();

    @Autowired
    private SpringApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, NotificationProcessor> processors = applicationContext.getBeansOfType(NotificationProcessor.class);
        for (String beanName : processors.keySet()) {
            NotificationProcessor processor = processors.get(beanName);
            if (processor.getSupportedTypes() != null) {
                for (NotificationTypeEnum type : processor.getSupportedTypes()) {
                    Assert.isTrue(!notificationProcessors.containsKey(type), "Duplicate notification processor for notification type: " + type);
                    notificationProcessors.put(type, processor);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Notification> NotificationProcessor<T> getNotificationProcessor(NotificationTypeEnum notificationType) {
        return notificationProcessors.get(notificationType);
    }

}
