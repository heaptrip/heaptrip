package com.heaptrip.repository.account.helper;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationQueryHelper extends AbstractQueryHelper<NotificationCriteria, Notification> {

	@Override
	public Class<NotificationCriteria> getCriteriaClass() {
		return NotificationCriteria.class;
	}

	@Override
	public String getQuery(NotificationCriteria criteria) {
		String query = null;

		if (criteria.getFromId() != null) {
			query = "{fromId: #";
		}

        if (criteria.getToId() != null) {
            query = ((query == null) ? "{toId: #" : query + ", toId: #");
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
	public Object[] getParameters(NotificationCriteria criteria, Object... arg) {
		List<Object> parameters = new ArrayList<>();

		if (criteria.getFromId() != null) {
			parameters.add(criteria.getFromId());
		}

        if (criteria.getToId() != null) {
            parameters.add(criteria.getToId());
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
	public String getHint(NotificationCriteria criteria) {
		return "{toId: 1, created: -1}";
	}

	@Override
	public String getSort(NotificationCriteria criteria) {
		return "{created: 1}";
	}

	@Override
	public String getProjection(NotificationCriteria criteria) {
		return null;
	}

    @Override
    protected Class<Notification> getCollectionClass() {
        return Notification.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.NOTIFICATIONS.getName();
    }
}
