package com.heaptrip.repository.account.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.repository.helper.QueryHelper;

@Service
public class NotificationQueryHelper implements QueryHelper<NotificationCriteria> {

	@Override
	public Class<NotificationCriteria> getCriteriaClass() {
		return NotificationCriteria.class;
	}

	@Override
	public String getQuery(NotificationCriteria criteria) {
		String query = null;

		if (criteria.getToId() != null) {
			query = "{toId: #";
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
}
