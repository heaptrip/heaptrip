package com.heaptrip.repository.content.helper;

import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

public class QueryHelperFactory {

	public static final int FEED_HELPER = 0;
	public static final int MY_ACCOUNT_HELPER = 1;
	public static final int FOREIGN_ACCOUNT_HELPER = 2;

	private static final QueryHelper<FeedCriteria> feedQueryHelper = new FeedQueryHelper();
	private static final QueryHelper<MyAccountCriteria> myAccountQueryHelper = new MyAccountQueryHelper();
	private static final QueryHelper<ForeignAccountCriteria> foreignAccountQueryHelper = new ForeignAccountQueryHelper();

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
