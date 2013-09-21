package com.heaptrip.service.content;

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.criteria.TextSearchCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;

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

	@DataProvider(name = "favoritesTripMyAccountCriteria")
	public static Object[][] getFavoritesTripMyAccountCriteria() {
		TripMyAccountCriteria criteria = new TripMyAccountCriteria();
		criteria.setUserId(USER_ID);
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.RATING);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.FAVORITES);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "textSearchCriteria")
	public static Object[][] getTextSearchCriteria() {
		TextSearchCriteria criteria = new TextSearchCriteria();
		criteria.setContentType(ContentEnum.TRIP);
		criteria.setUserId(USER_ID);
		criteria.setCategoryIds(new String[] { CATEGORY_IDS[0], CATEGORY_IDS[1] });
		if (!ArrayUtils.isEmpty(ContentSearchServiceTest.REGION_IDS)) {
			criteria.setRegionIds(new String[] { ContentSearchServiceTest.REGION_IDS[0] });
		}
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setQuery("name summary description");
		criteria.setTextLength(300L);
		return new Object[][] { new Object[] { criteria } };
	}
}
