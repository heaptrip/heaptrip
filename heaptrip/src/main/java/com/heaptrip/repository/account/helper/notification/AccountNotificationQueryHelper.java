package com.heaptrip.repository.account.helper.notification;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountNotificationQueryHelper extends AbstractQueryHelper<AccountNotificationCriteria, Notification> {
    @Override
    protected Class<Notification> getCollectionClass() {
        return Notification.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.NOTIFICATIONS.getName();
    }

    @Override
    public Class<AccountNotificationCriteria> getCriteriaClass() {
        return AccountNotificationCriteria.class;
    }

    @Override
    public String getQuery(AccountNotificationCriteria criteria) {
        String query = null;

        if (criteria.getAccountId() != null) {
            query = "{accountIds: #";
        }

        if (criteria.getStatus() != null) {
            query = ((query == null) ? "{status: #" : query + ", status: #");
        }

        if (criteria.getType() != null) {
            query = ((query == null) ? "{type: #" : query + ", type: #");
        }

        query = ((query == null) ? "{}" : query + "}");

        return query;
    }

    @Override
    public Object[] getParameters(AccountNotificationCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();

        if (criteria.getAccountId() != null) {
            parameters.add(criteria.getAccountId());
        }

        if (criteria.getStatus() != null) {
            parameters.add(criteria.getStatus());
        }

        if (criteria.getType() != null) {
            parameters.add(criteria.getType());
        }

        return parameters.toArray();
    }

    @Override
    public String getProjection(AccountNotificationCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(AccountNotificationCriteria criteria) {
        return "{accountIds: 1, created: -1}";
    }

    @Override
    public String getSort(AccountNotificationCriteria criteria) {
        return "{created: -1}";
    }
}
