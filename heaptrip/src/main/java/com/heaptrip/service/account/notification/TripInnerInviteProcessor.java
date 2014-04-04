package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import org.springframework.stereotype.Service;


@Service
public class TripInnerInviteProcessor implements NotificationProcessor<TripNotification> {

    @Override
    public NotificationTypeEnum[] getSupportedTypes() {
        return new NotificationTypeEnum[]{NotificationTypeEnum.TRIP_INNER_INVITE};
    }

    @Override
    public MultiLangText getNotificationText(TripNotification notification) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void accept(TripNotification notification) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reject(TripNotification notification) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
