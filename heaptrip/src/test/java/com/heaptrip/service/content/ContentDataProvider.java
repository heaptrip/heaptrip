package com.heaptrip.service.content;

import java.util.Locale;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.service.content.ContentSortEnum;
import com.heaptrip.domain.service.content.FeedCriteria;

public class ContentDataProvider {

	private static final String USER_ID = InitContentTest.USER_ID;

	private static String[] CATEGORY_IDS = InitContentTest.CATEGORY_IDS;

	@DataProvider(name = "feedCriteria")
	public static Object[][] getFeedCriteria() {
		FeedCriteria criteria = new FeedCriteria();
		criteria.setUserId(USER_ID);
		criteria.setCategoryIds(new String[] { CATEGORY_IDS[0] });
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.CREATED);
		criteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { criteria } };
	}
}
