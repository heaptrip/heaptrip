package com.heaptrip.repository.trip.helper;

import com.heaptrip.domain.service.content.ContentCriteria;
import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;
import com.heaptrip.domain.service.trip.MyAccountTripCriteria;

public class QueryHelperFactory {

	public static final int FEED_HELPER = 0;
	public static final int MY_ACCOUNT_HELPER = 1;
	public static final int FOREIGN_ACCOUNT_HELPER = 2;

	private static final QueryHelper<FeedTripCriteria> feedQueryHelper = new FeedQueryHelper();
	private static final QueryHelper<MyAccountTripCriteria> myAccountQueryHelper = new MyAccountQueryHelper();
	private static final QueryHelper<ForeignAccountTripCriteria> foreignAccountQueryHelper = new ForeignAccountQueryHelper();

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
