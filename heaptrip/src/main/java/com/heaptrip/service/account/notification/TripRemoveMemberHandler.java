package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.NotificationTemplate;
import com.heaptrip.domain.entity.account.notification.NotificationTemplateStorage;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Locale;

@Service
public class TripRemoveMemberHandler implements NotificationHandler<TripNotification> {

    @Autowired
    private NotificationTemplateStorage notificationTemplateStorage;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private AccountStoreService accountStoreService;

    @Override
    public NotificationTypeEnum[] getSupportedTypes() {
        return new NotificationTypeEnum[]{NotificationTypeEnum.TRIP_REMOVE_MEMBER, NotificationTypeEnum.TRIP_MEMBER_REFUSE};
    }

    @Override
    public MultiLangText getNotificationText(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");


        MultiLangText text = new MultiLangText();

        NotificationTemplate notificationTemplate = notificationTemplateStorage.getNotificationTemplate(notification.getType());
        if (notificationTemplate != null && notificationTemplate.getText() != null) {

            String senderName = accountStoreService.findOne(notification.getFromId()).getName();
            MultiLangText contentName = contentRepository.getName(notification.getContentId());

            for (String lang : notificationTemplate.getText().keySet()) {
                String template = notificationTemplate.getText().get(lang);
                String message = String.format(template, senderName, contentName.getValue(new Locale(lang)));
                text.setValue(message, new Locale(lang));
            }
        }

        return text;
    }

    @Override
    public String[] getAllowed(TripNotification notification) {
        return new String[0];
    }

    @Override
    public void accept(TripNotification notification) {

    }

    @Override
    public void reject(TripNotification notification) {

    }
}
