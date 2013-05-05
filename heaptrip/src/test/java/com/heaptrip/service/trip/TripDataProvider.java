package com.heaptrip.service.trip;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.util.RandomUtils;

public class TripDataProvider {

	@DataProvider(name = "tripCriteriaForFeed")
	public static Object[][] getTripCriteriaForFeed() {
		TripCriteria tripCriteria = new TripCriteria();
		tripCriteria.setUserId(InitTripTest.USER_ID);
		tripCriteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[0] });
		tripCriteria.setSkip(0L);
		tripCriteria.setLimit(InitTripTest.TRIPS_COUNT);
		tripCriteria.setSort(ContentSortEnum.CREATED);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		tripCriteria.setPeriod(period);
		tripCriteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { tripCriteria } };
	}

	@DataProvider(name = "tripCriteriaForMyAccount")
	public static Object[][] getTripCriteriaForMyAccount() {
		TripCriteria tripCriteria = new TripCriteria();
		tripCriteria.setOwnerId(InitTripTest.OWNER_ID);
		tripCriteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[1] });
		tripCriteria.setSkip(0L);
		tripCriteria.setLimit(InitTripTest.TRIPS_COUNT);
		tripCriteria.setSort(ContentSortEnum.RATING);
		tripCriteria.setStatus(new ContentStatusEnum[] { ContentStatusEnum.DRAFT });
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		tripCriteria.setPeriod(period);
		tripCriteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { tripCriteria } };
	}

	@DataProvider(name = "tripCriteriaForNotMyAccount")
	public static Object[][] getTripCriteriaForNotMyAccount() {
		TripCriteria tripCriteria = new TripCriteria();
		tripCriteria.setOwnerId(InitTripTest.OWNER_ID);
		tripCriteria.setUserId(InitTripTest.USER_ID);
		tripCriteria.setCategoryIds(new String[] { InitTripTest.CATEGORY_IDS[0] });
		tripCriteria.setSkip(0L);
		tripCriteria.setLimit(InitTripTest.TRIPS_COUNT);
		tripCriteria.setSort(ContentSortEnum.CREATED);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		tripCriteria.setPeriod(period);
		tripCriteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { tripCriteria } };
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

	@DataProvider(name = "tableItem")
	private static Object[][] getTableItem() {
		TableItem item = new TableItem();
		TableUser[] users = new TableUser[] { new TableUser(InitTripTest.USER_ID), new TableUser(InitTripTest.OWNER_ID) };
		item.setUsers(users);
		return new Object[][] { new Object[] { item } };
	}
}
