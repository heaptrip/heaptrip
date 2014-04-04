package com.heaptrip.service.account.notification;

import com.heaptrip.domain.service.account.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class NotificationServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private NotificationService notificationService;

    @Test
    public void addTripUser() {
        //TripNotification notification = new TripNotification();
        //ZnotificationService.addNotification(notification);
    }

}
