package com.heaptrip.repository.account.helper.notification;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityNotificationQueryHelper extends AbstractQueryHelper<CommunityNotificationCriteria, Notification> {
    @Override
    protected Class<Notification> getCollectionClass() {
        return Notification.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.NOTIFICATIONS.getName();
    }

    @Override
    public Class<CommunityNotificationCriteria> getCriteriaClass() {
        return CommunityNotificationCriteria.class;
    }

    @Override
    public String getQuery(CommunityNotificationCriteria criteria) {
        String query = null;

        if (criteria.getToIds() != null) {
            query = "{toId: #}";
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
    public Object[] getParameters(CommunityNotificationCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();

        if (criteria.getToIds() != null) {
            parameters.add(criteria.getToIds());
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
    public String getProjection(CommunityNotificationCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(CommunityNotificationCriteria criteria) {
        return "{toId: 1, created: -1}";
    }

    @Override
    public String getSort(CommunityNotificationCriteria criteria) {
        return "{created: 1}";
    }
}
