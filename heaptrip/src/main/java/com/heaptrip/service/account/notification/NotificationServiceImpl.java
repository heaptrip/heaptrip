package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;
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
    private ErrorService errorService;

    @Autowired
    private NotificationHandlerFactory notificationHandlerFactory;

    @Autowired
    private RelationRepository relationRepository;

    @Override
    public Notification addNotification(Notification notification) {
        Assert.notNull(notification, "notification must not be null");
        Assert.notNull(notification.getFromId(), "notification.fromId must not be null");
        Assert.notNull(notification.getToId(), "notification.getToId must not be null");
        Assert.notNull(notification.getType(), "notification.type must not be null");

        NotificationHandler notificationProcessor = notificationHandlerFactory.getNotificationHandler(notification.getType());
        if (notificationProcessor != null) {
            MultiLangText text = notificationProcessor.getNotificationText(notification);
            notification.setText(text);
        }

        // TODO dikma необходимо заполнять поля соответственно типу оповещения

        String[] ids = new String[2];
        ids[0] = notification.getFromId();
        ids[1] = notification.getToId();
        notification.setAccountIds(ids);

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findByNotificationCriteria(NotificationCriteria criteria) {
        Assert.notNull(criteria, "notificationCriteria must not be null");
        Assert.notNull(criteria.getFromId(), "notification.fromId must not be null");
        Assert.notNull(criteria.getToId(), "notification.getToId must not be null");
        return notificationRepository.findByNotificationCriteria(criteria);
    }

    @Override
    public long countByNotificationCriteria(NotificationCriteria criteria) {
        Assert.notNull(criteria, "notificationCriteria must not be null");
        Assert.notNull(criteria.getFromId(), "notification.fromId must not be null");
        Assert.notNull(criteria.getToId(), "notification.getToId must not be null");
        return notificationRepository.countByNotificationCriteria(criteria);
    }

//    @Override
//    public List<Notification> getNotificationsByAccountNotificationCriteria(AccountNotificationCriteria criteria) {
//        Assert.notNull(criteria, "accountNotificationCriteria must not be null");
//        return notificationRepository.findNotificationsByAccountNotificationCriteria(criteria);
//    }
//
//    @Override
//    public long getCountByAccountNotificationCriteria(AccountNotificationCriteria criteria) {
//        Assert.notNull(criteria, "accountNotificationCriteria must not be null");
//        return notificationRepository.countByAccountNotificationCriteria(criteria);
//    }
//
//    @Override
//    public List<Notification> getNotificationsByCommunityNotificationCriteria(CommunityNotificationCriteria criteria) {
//        Assert.notNull(criteria, "communityNotificationCriteria must not be null");
//
//        List<String> ids = getCommunityId(criteria.getUserId());
//
//        if (ids != null && ids.size() > 0) {
//            criteria.setToIds((String[]) ids.toArray());
//            return notificationRepository.findNotificationsByCommunityNotificationCriteria(criteria);
//        } else {
//            return new ArrayList<>();
//        }
//    }
//
//    @Override
//    public long getCountByCommunityNotificationCriteria(CommunityNotificationCriteria criteria) {
//        Assert.notNull(criteria, "communityNotificationCriteria must not be null");
//        Assert.notNull(criteria.getUserId(), "communityNotificationCriteria.getUserId() must not be null");
//
//        List<String> ids = getCommunityId(criteria.getUserId());
//
//        if (ids != null && ids.size() > 0) {
//            criteria.setToIds((String[]) ids.toArray());
//            return notificationRepository.countByCommunityNotificationCriteria(criteria);
//        } else {
//            return 0;
//        }
//    }
//
//    private List<String> getCommunityId(String userId) {
//        List<String> ids = new ArrayList<>();
//        List<Relation> relations;
//
//        RelationCriteria relationCriteria = new RelationCriteria();
//        relationCriteria.setFromId(userId);
//        relationCriteria.setTypeRelation(RelationTypeEnum.OWNER);
//
//        relations = relationRepository.findByCriteria(relationCriteria);
//
//        if (relations != null && relations.size() > 0) {
//            for (Relation relation : relations) {
//                ids.add(relation.getToId());
//            }
//        }
//
//        relationCriteria.setTypeRelation(RelationTypeEnum.EMPLOYEE);
//
//        relations = relationRepository.findByCriteria(relationCriteria);
//
//        if (relations != null && relations.size() > 0) {
//            for (Relation relation : relations) {
//                ids.add(relation.getToId());
//            }
//        }
//    }

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
            }
        } else if (status.equals(NotificationStatusEnum.REJECTED)) {
            NotificationHandler notificationHandler = notificationHandlerFactory.getNotificationHandler(notification.getType());
            if (notificationHandler != null) {
                notificationHandler.reject(notification);
            }
        }
    }
}
