package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.util.converter.Converter;
import com.heaptrip.util.converter.ListConverter;
import com.heaptrip.web.model.profile.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 18.04.14
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NotificationModelServiceImpl extends BaseModelTypeConverterServiceImpl implements NotificationModelService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ProfileModelService profileModelService;

    @Override
    public List<NotificationModel> getNotificationByCriteria(NotificationCriteria notificationCriteria) {
        List<Notification> notifications = notificationService.findByNotificationCriteria(notificationCriteria);
        return convertNotificationsToNotificationModels(notifications);
    }

    private NotificationModel convertNotificationToNotificationModel(Notification notification) {
        NotificationModel model = null;
        if (notification != null) {
            model = new NotificationModel();
            model.setId(notification.getId());
            model.setCreated(convertDate(notification.getCreated()));
            model.setText(getMultiLangTextValue(notification.getText(), getCurrentLocale(), false));
            model.setStatus(notification.getStatus().name());
            model.setType(notification.getType().name());
            model.setAccountFrom(profileModelService.getAccountInformation(notification.getFromId()));
            model.setAccountTo(profileModelService.getAccountInformation(notification.getToId()));
        }
        return model;
    }

    private List<NotificationModel> convertNotificationsToNotificationModels(List<Notification> notifications) {
        return ListConverter.convertList(notifications, new Converter<Notification, NotificationModel>() {
            public NotificationModel convert(Notification notification) {
                return convertNotificationToNotificationModel(notification);
            }
        });
    }


}
