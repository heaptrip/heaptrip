package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTemplate;
import com.heaptrip.domain.entity.account.notification.NotificationTemplateStorage;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.system.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;

@Service
public class TripRequestHandler implements NotificationHandler<TripNotification> {

    @Autowired
    private NotificationTemplateStorage notificationTemplateStorage;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TripUserService tripUserService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private RelationRepository relationRepository;

    protected static final Logger logger = LoggerFactory.getLogger(RelationHandler.class);

    @Override
    public NotificationTypeEnum[] getSupportedTypes() {
        return new NotificationTypeEnum[]{NotificationTypeEnum.TRIP_REQUEST};
    }

    @Override
    public MultiLangText getNotificationText(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");


        MultiLangText text = new MultiLangText();

        NotificationTemplate notificationTemplate = notificationTemplateStorage.getNotificationTemplate(NotificationTypeEnum.TRIP_REQUEST);
        if (notificationTemplate != null && notificationTemplate.getText() != null) {

            String senderName = accountStoreService.findOne(notification.getFromId()).getName();
            String receiverName = accountStoreService.findOne(notification.getToId()).getName();
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
        String[] ids = null;
        Account account = accountStoreService.findOne(notification.getToId());

        if (account == null) {
            String msg = String.format("account not find by id %s", notification.getToId());
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
        } else if (!account.getAccountType().toString().equals(AccountEnum.USER.toString())) {
            String[] typeRelations = new String[2];
            typeRelations[0] = RelationTypeEnum.OWNER.toString();
            typeRelations[1] = RelationTypeEnum.EMPLOYEE.toString();

            List<Relation> relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(notification.getToId(), typeRelations));

            if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                ids = relations.get(0).getUserIds();
            } else {
                String msg = String.format("community not have owner and employee: %s", notification.getToId());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_HAVE_OWNER_AND_EMPLOYEE);
            }

            return ids;
        } else {
            return ids;
        }
    }

    @Override
    public void accept(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");

        String tripId = notification.getContentId();
        String tableId = notification.getTableId();
        String userId = notification.getFromId();

        boolean isTableUser = tripUserService.isTripTableMember(tripId, tableId, userId);
        Assert.isTrue(isTableUser, String.format("user:%s not found in trip:%s and table:%s", userId, tripId, tableId));

        tripUserService.acceptTripMember(tripId, tableId, userId);
    }

    @Override
    public void reject(TripNotification notification) {
        Assert.notNull(notification.getContentId(), "notification.contentId must not be null");
        Assert.notNull(notification.getTableId(), "notification.tableId must not be null");

        String tripId = notification.getContentId();
        String tableId = notification.getTableId();
        String userId = notification.getFromId();

        boolean isTableUser = tripUserService.isTripTableMember(tripId, tableId, userId);
        Assert.isTrue(isTableUser, String.format("user:%s not found in trip:%s and table:%s", userId, tripId, tableId));

        tripUserService.removeTripMember(tripId, tableId, userId);
    }
}
