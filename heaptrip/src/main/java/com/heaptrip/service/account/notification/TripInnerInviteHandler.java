package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.NotificationTemplate;
import com.heaptrip.domain.entity.account.notification.NotificationTemplateStorage;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;


@Service
public class TripInnerInviteHandler implements NotificationHandler<TripNotification> {

    @Autowired
    private NotificationTemplateStorage notificationTemplateStorage;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TripUserService tripUserService;

    @Override
    public NotificationTypeEnum[] getSupportedTypes() {
        return new NotificationTypeEnum[]{NotificationTypeEnum.TRIP_INNER_INVITE};
    }

    @Override
    public MultiLangText getNotificationText(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");


        MultiLangText text = new MultiLangText();

        NotificationTemplate notificationTemplate = notificationTemplateStorage.getNotificationTemplate(NotificationTypeEnum.TRIP_INNER_INVITE);
        if (notificationTemplate != null && notificationTemplate.getText() != null) {

            String senderName = accountRepository.getName(notification.getFromId());
            String receiverName = accountRepository.getName(notification.getToId());
            MultiLangText contentName = contentRepository.getName(notification.getContentId());

            for (String lang : notificationTemplate.getText().keySet()) {
                String template = notificationTemplate.getText().get(lang);
                String message = String.format(template, senderName, receiverName, contentName.getValue(new Locale(lang)));
                text.setValue(message, new Locale(lang));
            }
        }

        return text;
    }

    @Override
    public String[] getAllowed(TripNotification notification) {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void accept(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");

        TripMemberCriteria tripMemberCriteria = new TripMemberCriteria();
        tripMemberCriteria.setTripId(notification.getContentId());
        tripMemberCriteria.setTableId(notification.getTableId());
        tripMemberCriteria.setUserId(notification.getToId());

        List<TripMember> members = tripUserService.getMembersByCriteria(tripMemberCriteria);
        Assert.notNull(members, "trip member is not found");
        Assert.isTrue(!members.isEmpty(), "trip member is not found");
        Assert.isTrue(members.size() == 1, "duplicate member per table item");
        tripUserService.acceptTripUser(members.get(0).getId());
    }

    @Override
    public void reject(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");

        TripMemberCriteria tripMemberCriteria = new TripMemberCriteria();
        tripMemberCriteria.setTripId(notification.getContentId());
        tripMemberCriteria.setTableId(notification.getTableId());
        tripMemberCriteria.setUserId(notification.getToId());

        List<TripMember> members = tripUserService.getMembersByCriteria(tripMemberCriteria);
        Assert.notNull(members, "trip member is not found");
        Assert.isTrue(!members.isEmpty(), "trip member is not found");
        Assert.isTrue(members.size() == 1, "duplicate member per table item");
        tripUserService.removeTripMember(members.get(0).getId());
    }
}
