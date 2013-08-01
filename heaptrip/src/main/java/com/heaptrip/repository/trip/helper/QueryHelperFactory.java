package com.heaptrip.repository.trip.helper;

import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;

public class QueryHelperFactory {

	public static final int FEED_HELPER = 0;
	public static final int MY_ACCOUNT_HELPER = 1;
	public static final int FOREIGN_ACCOUNT_HELPER = 2;

	private static final QueryHelper<TripFeedCriteria> feedQueryHelper = new FeedQueryHelper();
	private static final QueryHelper<TripMyAccountCriteria> myAccountQueryHelper = new MyAccountQueryHelper();
	private static final QueryHelper<TripForeignAccountCriteria> foreignAccountQueryHelper = new ForeignAccountQueryHelper();

	@SuppressWarnings("unchecked")
	public static <T extends ContentCriteria> QueryHelper<T> getInstance(int helperType) {
		switch (helperType) {
		case FEED_HELPER:
			return (QueryHelper<T>) feedQueryHelper;
		case MY_ACCOUNT_HELPER:
			return (QueryHelper<T>) myAccountQueryHelper;
		case FOREIGN_ACCOUNT_HELPER:
			return (QueryHelper<T>) foreignAccountQueryHelper;
		default:
			return null;
		}
	}

}
