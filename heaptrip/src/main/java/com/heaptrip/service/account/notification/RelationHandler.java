package com.heaptrip.service.account.notification;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationTemplate;
import com.heaptrip.domain.entity.account.notification.NotificationTemplateStorage;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.domain.service.system.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RelationHandler implements NotificationHandler<Notification> {

    @Autowired
    private NotificationTemplateStorage notificationTemplateStorage;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private ErrorService errorService;

    protected static final Logger logger = LoggerFactory.getLogger(RelationHandler.class);

    @Override
    public NotificationTypeEnum[] getSupportedTypes() {
        return new NotificationTypeEnum[]{NotificationTypeEnum.FRIEND, NotificationTypeEnum.MEMBER, NotificationTypeEnum.EMPLOYEE, NotificationTypeEnum.OWNER};
    }

    @Override
    public MultiLangText getNotificationText(Notification notification) {
        MultiLangText text = new MultiLangText();

        NotificationTemplate notificationTemplate = notificationTemplateStorage.getNotificationTemplate(notification.getType());
        if (notificationTemplate != null && notificationTemplate.getText() != null) {

            Account sender = accountStoreService.findOne(notification.getFromId());
            Account receiver = accountStoreService.findOne(notification.getToId());

            for (String lang : notificationTemplate.getText().keySet()) {
                String template = notificationTemplate.getText().get(lang);
                String message = String.format(template, sender.getName(), receiver.getName());
                text.setValue(message, new Locale(lang));
            }
        }

        return text;
    }

    @Override
    public String[] getAllowed(Notification notification) {
        String[] ids = null;
        String[] typeRelations;

        if (notification.getType().equals(NotificationTypeEnum.MEMBER)) {
            typeRelations = new String[2];
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
        } else if (notification.getType().equals(NotificationTypeEnum.EMPLOYEE) || notification.getType().equals(NotificationTypeEnum.OWNER)) {
            typeRelations = new String[1];
            typeRelations[0] = RelationTypeEnum.OWNER.toString();

            List<Relation> relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(notification.getToId(), typeRelations));

            if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                ids = relations.get(0).getUserIds();
            } else {
                String msg = String.format("community not have owner: %s", notification.getToId());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_HAVE_OWNER);
            }
        }

        return ids;
    }

    @Override
    public void accept(Notification notification) {
        String[] types;
        List<Relation> relations;

        if (notification.getType().equals(NotificationTypeEnum.FRIEND)) {
            relationRepository.add(notification.getFromId(), notification.getToId(), RelationTypeEnum.FRIEND);
            relationRepository.add(notification.getToId(), notification.getFromId(), RelationTypeEnum.FRIEND);
            accountStoreService.update(notification.getFromId());
            accountStoreService.update(notification.getToId());
        } else if (notification.getType().equals(NotificationTypeEnum.EMPLOYEE)) {
            // если он уже владелец, то отказать, если участник, то заменить тип связи на работник
            types = new String[3];
            types[0] = RelationTypeEnum.OWNER.toString();
            types[1] = RelationTypeEnum.EMPLOYEE.toString();
            types[2] = RelationTypeEnum.MEMBER.toString();

            relations = relationRepository.findByRelationCriteria(new RelationCriteria(notification.getToId(), notification.getFromId(), types));

            if (relations != null && relations.size() > 0) {
                for (Relation relation : relations) {
                    if (relation.getType().toString().equals(RelationTypeEnum.OWNER.toString()))  {
                        String msg = String.format("relation already exists");
                        logger.debug(msg);
                        throw errorService.createException(AccountException.class, ErrorEnum.ERROR_RELATION_ALREADY_EXISTS);
                    } else if (relation.getType().toString().equals(RelationTypeEnum.EMPLOYEE.toString())) {
                        logger.debug("User is already employee");
                    } else {
                        relationRepository.remove(notification.getToId(), notification.getFromId() , RelationTypeEnum.MEMBER);
                        relationRepository.add(notification.getToId(), notification.getFromId() , RelationTypeEnum.EMPLOYEE);
                        accountStoreService.update(notification.getFromId());
                    }
                }
            } else {
                relationRepository.add(notification.getToId(), notification.getFromId(), RelationTypeEnum.EMPLOYEE);
                accountStoreService.update(notification.getFromId());
            }
        } else if (notification.getType().equals(NotificationTypeEnum.MEMBER)) {
            // если он уже владелец или работник, то отказать
            types = new String[3];
            types[0] = RelationTypeEnum.OWNER.toString();
            types[1] = RelationTypeEnum.EMPLOYEE.toString();
            types[2] = RelationTypeEnum.MEMBER.toString();

            relations = relationRepository.findByRelationCriteria(new RelationCriteria(notification.getToId(), notification.getFromId(), types));

            if (relations != null && relations.size() > 0) {
                for (Relation relation : relations) {
                    if (relation.getType().toString().equals(RelationTypeEnum.OWNER.toString()) || relation.getType().toString().equals(RelationTypeEnum.EMPLOYEE.toString()))  {
                        String msg = String.format("relation already exists");
                        logger.debug(msg);
                        throw errorService.createException(AccountException.class, ErrorEnum.ERROR_RELATION_ALREADY_EXISTS);
                    } else if (relation.getType().toString().equals(RelationTypeEnum.MEMBER.toString())) {
                        logger.debug("User is already member");
                    }
                }
            } else {
                relationRepository.add(notification.getToId(), notification.getFromId(), RelationTypeEnum.MEMBER);
                accountStoreService.update(notification.getFromId());
            }
        } else if (notification.getType().equals(NotificationTypeEnum.OWNER)) {
            // если он уже участник, работник или владелец удаляем и создаем новую связь
            types = new String[3];
            types[0] = RelationTypeEnum.OWNER.toString();
            types[1] = RelationTypeEnum.EMPLOYEE.toString();
            types[2] = RelationTypeEnum.MEMBER.toString();

            relations = relationRepository.findByRelationCriteria(new RelationCriteria(notification.getToId(), notification.getFromId(), types));

            if (relations != null && relations.size() > 0) {
                for (Relation relation : relations) {
                    relationRepository.remove(relation.getFromId(), notification.getFromId(), relation.getType());
                }
            }

            relationRepository.add(notification.getToId(), notification.getFromId(), RelationTypeEnum.OWNER);
            accountStoreService.update(notification.getFromId());
        }
    }

    @Override
    public void reject(Notification notification) {
        // do nothing ;)
    }
}
