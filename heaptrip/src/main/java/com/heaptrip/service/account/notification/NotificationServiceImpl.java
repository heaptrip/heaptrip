package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.CommunityNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.system.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    protected static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private NotificationHandlerFactory notificationHandlerFactory;

    @Override
    public Notification addNotification(Notification notification) {
        Assert.notNull(notification, "notification must not be null");
        Assert.notNull(notification.getFromId(), "notification.fromId must not be null");
        Assert.notNull(notification.getToId(), "notification.toId must not be null");
        Assert.notNull(notification.getType(), "notification.type must not be null");

        NotificationHandler notificationProcessor = notificationHandlerFactory.getNotificationHandler(notification.getType());
        if (notificationProcessor != null) {
            MultiLangText text = notificationProcessor.getNotificationText(notification);
            notification.setText(text);
        }

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByNotificationCriteria(NotificationCriteria criteria) {
        Assert.notNull(criteria, "notificationCriteria must not be null");
        return notificationRepository.findByNotificationCriteria(criteria);
    }

    @Override
    public long getCountByNotificationCriteria(NotificationCriteria criteria) {
        Assert.notNull(criteria, "notificationCriteria must not be null");
        return notificationRepository.countByNotificationCriteria(criteria);
    }

    @Override
    public List<Notification> getNotificationsByAccountNotificationCriteria(AccountNotificationCriteria criteria) {
        // TODO dikma
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCountByAccountNotificationCriteria(AccountNotificationCriteria criteria) {
        // TODO dikma
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Notification> getNotificationsByCommunityNotificationCriteria(CommunityNotificationCriteria criteria) {
        // TODO dikma
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCountByCommunityNotificationCriteria(CommunityNotificationCriteria criteria) {
        // TODO dikma
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void changeStatus(String notificationId, NotificationStatusEnum status) {
        Assert.notNull(notificationId, "notificationId must not be null");

        Notification notification = notificationRepository.findOne(notificationId);

        if (notification == null) {
            logger.debug("notification not find by id " + notificationId);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_NOTIFICATION_NOT_FOUND);
        } else if (!notification.getStatus().equals(NotificationStatusEnum.NEW)) {
            logger.debug("notification status must be: " + NotificationStatusEnum.NEW);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_NOTIFICATION_NOT_NEW);
        }

        notificationRepository.changeStatus(notificationId, status);

        if (status.equals(NotificationStatusEnum.ACCEPTED)) {
            NotificationHandler notificationHandler = notificationHandlerFactory.getNotificationHandler(notification.getType());
            if (notificationHandler != null) {
                notificationHandler.accept(notification);
            } else {
                // TODO dikma: move this codes to notification handlers
                String userId = null;

                if (notification.getType().equals(NotificationTypeEnum.FRIEND)) {
                    relationRepository.save(Relation.getRelation(notification.getFromId(), notification.getToId(), TypeRelationEnum.FRIEND));
                    userId = notification.getToId();
                } else if (notification.getType().equals(NotificationTypeEnum.EMPLOYEE)) {
                    relationRepository.save(Relation.getRelation(notification.getFromId(), notification.getToId(), TypeRelationEnum.EMPLOYEE));
                    userId = notification.getFromId();
                } else if (notification.getType().equals(NotificationTypeEnum.MEMBER)) {
                    relationRepository.save(Relation.getRelation(notification.getFromId(), notification.getToId(), TypeRelationEnum.MEMBER));
                    userId = notification.getFromId();
                } else if (notification.getType().equals(NotificationTypeEnum.OWNER)) {
                    relationRepository.save(Relation.getRelation(notification.getFromId(), notification.getToId(), TypeRelationEnum.OWNER));
                    userId = notification.getFromId();
                }

                if (userId != null) {
                    accountStoreService.update(userId);
                }
            }

        } else if (status.equals(NotificationStatusEnum.REJECTED)) {
            NotificationHandler notificationHandler = notificationHandlerFactory.getNotificationHandler(notification.getType());
            if (notificationHandler != null) {
                notificationHandler.reject(notification);
            }
        }
    }
}
