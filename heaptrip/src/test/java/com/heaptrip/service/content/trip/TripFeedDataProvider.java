package com.heaptrip.service.content.trip;

import java.util.Calendar;
import java.util.Locale;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;

public class TripFeedDataProvider {

	static String[] CATEGORY_IDS = new String[] { "2.4.7", "3.2" };

	static String[] REGION_IDS = new String[] { "1", "7", "8" };

	static String CONTENT_ID = "CONTENT_FOR_TRIP_FEED_SERVICE_TEST";

	static String OWNER_ID = "OWNER_FOR_TRIP_FEED_SERVICE_TEST";

	static String USER_ID = "USER_FOR_TRIP_FEED_SERVICE_TEST";

	@DataProvider(name = "feedCriteria")
	public static Object[][] getTripFeedCriteria() {
		TripFeedCriteria criteria = new TripFeedCriteria();
		criteria.setUserId(USER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.CREATED);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		criteria.setPeriod(period);
		criteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "myAccountCriteria")
	public static Object[][] getTripMyAccountCriteria() {
		TripMyAccountCriteria criteria = new TripMyAccountCriteria();
		criteria.setUserId(OWNER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.RATING);
		criteria.setStatus(new ContentStatusEnum[] { ContentStatusEnum.PUBLISHED_FRIENDS });
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		criteria.setPeriod(period);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.OWN);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "foreignAccountCriteria")
	public static Object[][] getTripForeignAccountCriteria() {
		TripForeignAccountCriteria criteria = new TripForeignAccountCriteria();
		criteria.setAccountId(OWNER_ID);
		criteria.setUserId(USER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.CREATED);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		criteria.setPeriod(period);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.OWN);
		return new Object[][] { new Object[] { criteria } };
	}

}
