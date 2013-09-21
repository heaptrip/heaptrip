package com.heaptrip.repository.account.helper;

import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.criteria.Criteria;

public class QueryHelperFactory {
	
	public static final int NOTIFICATION_HELPER = 0;

	private static final QueryHelper<NotificationCriteria> notificationQueryHelper = new NotificationQueryHelper();

	@SuppressWarnings("unchecked")
	public static <T extends Criteria> QueryHelper<T> getInstance(int helperType) {
		switch (helperType) {
		case NOTIFICATION_HELPER:
			return (QueryHelper<T>) notificationQueryHelper;
		default:
			return null;
		}
	}
}
