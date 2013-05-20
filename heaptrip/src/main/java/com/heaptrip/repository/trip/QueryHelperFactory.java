package com.heaptrip.repository.trip;

public class QueryHelperFactory {

	public static final int FEED_HELPER = 0;
	public static final int MY_ACCOUNT_HELPER = 1;
	public static final int NOT_MY_ACCOUNT_HELPER = 2;
	public static final int MEMBER_HELPER = 3;

	private static final QueryHelper feedQueryHelper = new FeedQueryHelper();
	private static final QueryHelper myAccountQueryHelper = new MyAccountQueryHelper();
	private static final QueryHelper notMyAccountQueryHelper = new NotMyAccountQueryHelper();
	private static final QueryHelper memberQueryHelper = new MemberQueryHelper();

	public static QueryHelper getInstance(int helperType) {
		switch (helperType) {
		case FEED_HELPER:
			return feedQueryHelper;
		case MY_ACCOUNT_HELPER:
			return myAccountQueryHelper;
		case NOT_MY_ACCOUNT_HELPER:
			return notMyAccountQueryHelper;
		case MEMBER_HELPER:
			return memberQueryHelper;
		default:
			return null;
		}
	}

}
