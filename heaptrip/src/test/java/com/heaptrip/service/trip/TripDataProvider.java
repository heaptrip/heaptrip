package com.heaptrip.service.trip;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.content.ContentSortEnum;
import com.heaptrip.domain.service.content.RelationEnum;
import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;
import com.heaptrip.domain.service.trip.MyAccountTripCriteria;
import com.heaptrip.domain.service.trip.SearchPeriod;
import com.heaptrip.util.RandomUtils;

public class TripDataProvider {

	@DataProvider(name = "feedTripCriteria")
	public static Object[][] getFeedCriteria() {
		FeedTripCriteria criteria = new FeedTripCriteria();
		criteria.setUserId(InitTripTest.USER_ID);
		criteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[0] });
		criteria.setSkip(0L);
		criteria.setLimit(InitTripTest.TRIPS_COUNT);
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

	@DataProvider(name = "myAccountTripCriteria")
	public static Object[][] getMyAccountTripCriteria() {
		MyAccountTripCriteria criteria = new MyAccountTripCriteria();
		criteria.setUserId(InitTripTest.OWNER_ID);
		criteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[1] });
		criteria.setSkip(0L);
		criteria.setLimit(InitTripTest.TRIPS_COUNT);
		criteria.setSort(ContentSortEnum.RATING);
		criteria.setStatus(new ContentStatusEnum[] { ContentStatusEnum.DRAFT });
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

	@DataProvider(name = "foreignAccountTripCriteria")
	public static Object[][] getForeignAccountTripCriteria() {
		ForeignAccountTripCriteria criteria = new ForeignAccountTripCriteria();
		criteria.setOwnerId(InitTripTest.OWNER_ID);
		criteria.setUserId(InitTripTest.USER_ID);
		criteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[0] });
		criteria.setSkip(0L);
		criteria.setLimit(InitTripTest.TRIPS_COUNT);
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

	@DataProvider(name = "myAccountMemberTripCriteria")
	public static Object[][] getMyAccountMemberTripCriteria() {
		MyAccountTripCriteria criteria = new MyAccountTripCriteria();
		criteria.setUserId(InitTripTest.USER_ID);
		criteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[0] });
		criteria.setSkip(0L);
		criteria.setLimit(InitTripTest.TRIPS_COUNT);
		criteria.setSort(ContentSortEnum.RATING);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		criteria.setPeriod(period);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.MEMBER);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "myAccountFavoritesTripCriteria")
	public static Object[][] gettMyAccountFavoritesTripCriteria() {
		MyAccountTripCriteria criteria = new MyAccountTripCriteria();
		criteria.setUserId(InitTripTest.USER_ID);
		criteria.setSkip(0L);
		criteria.setLimit(InitTripTest.TRIPS_COUNT);
		criteria.setSort(ContentSortEnum.RATING);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.FAVORITES);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "tripWithTable")
	private static Object[][] getTripWithTable() {
		int tableSize = RandomUtils.getRandomInt(1, 10);
		TableItem[] table = new TableItem[tableSize];
		for (int j = 0; j < tableSize; j++) {
			TableItem item = new TableItem();
			Calendar startBegin = Calendar.getInstance();
			startBegin.set(2013, 0, 1);
			Calendar startEnd = Calendar.getInstance();
			startEnd.set(2013, 8, 1);
			Calendar dateEnd = Calendar.getInstance();
			dateEnd.set(2013, 11, 1);
			Date begin = RandomUtils.getRandomDate(startBegin.getTime(), startEnd.getTime());
			Date end = RandomUtils.getRandomDate(begin, dateEnd.getTime());
			item.setBegin(begin);
			item.setEnd(end);
			table[j] = item;
		}
		Trip trip = new Trip();
		trip.setTable(table);
		return new Object[][] { new Object[] { trip } };
	}
}
