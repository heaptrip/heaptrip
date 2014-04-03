package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import com.heaptrip.util.RandomUtils;
import org.testng.annotations.DataProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TripFeedDataProvider {

    static String[] CATEGORY_IDS = new String[]{"2.4.7", "3.2"};

    static String[] REGION_IDS = new String[]{"1", "7", "8"};

    static String CONTENT_ID = "CONTENT_FOR_TRIP_FEED_SERVICE_TEST";

    static String[] TABLE_IDs = new String[]{"0", "1", "2", "3", "4"};

    static String OWNER_ID = "OWNER_FOR_TRIP_FEED_SERVICE_TEST";

    static String USER_ID = "USER_FOR_TRIP_FEED_SERVICE_TEST";

    static TableItem[] getTable() {
        TableItem[] table = new TableItem[TABLE_IDs.length];
        for (int i = 0; i < TABLE_IDs.length; i++) {
            TableItem item = new TableItem();
            item.setId(TABLE_IDs[i]);
            Calendar startBegin = Calendar.getInstance();
            startBegin.set(2013, Calendar.JANUARY, 1);
            Calendar startEnd = Calendar.getInstance();
            startEnd.set(2013, Calendar.SEPTEMBER, 1);
            Calendar dateEnd = Calendar.getInstance();
            dateEnd.set(2013, Calendar.DECEMBER, 1);
            Date begin = RandomUtils.getRandomDate(startBegin.getTime(), startEnd.getTime());
            Date end = RandomUtils.getRandomDate(begin, dateEnd.getTime());
            item.setBegin(begin);
            item.setEnd(end);
            table[i] = item;
        }
        return table;
    }

    static Trip getTrip() {
        Trip trip = new Trip();
        trip.setId(TripFeedDataProvider.CONTENT_ID);
        trip.setOwnerId(TripFeedDataProvider.OWNER_ID);
        trip.setAllowed(new String[]{TripFeedDataProvider.USER_ID});
        trip.setCategoryIds(TripFeedDataProvider.CATEGORY_IDS);
        trip.setRegionIds(TripFeedDataProvider.REGION_IDS);
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
        trip.setStatus(status);
        trip.setTable(getTable());
        return trip;
    }


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
        begin.set(2013, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2013, Calendar.DECEMBER, 1);
        SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
        criteria.setPeriod(period);
        criteria.setLocale(Locale.ENGLISH);
        return new Object[][]{new Object[]{criteria}};
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
        criteria.setStatus(new ContentStatusEnum[]{ContentStatusEnum.PUBLISHED_FRIENDS});
        Calendar begin = Calendar.getInstance();
        begin.set(2013, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2013, Calendar.DECEMBER, 1);
        SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
        criteria.setPeriod(period);
        criteria.setLocale(Locale.ENGLISH);
        criteria.setRelation(RelationEnum.OWN);
        return new Object[][]{new Object[]{criteria}};
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
        begin.set(2013, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2013, Calendar.DECEMBER, 1);
        SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
        criteria.setPeriod(period);
        criteria.setLocale(Locale.ENGLISH);
        criteria.setRelation(RelationEnum.OWN);
        return new Object[][]{new Object[]{criteria}};
    }

    @DataProvider(name = "memberMyAccountCriteria")
    public static Object[][] getMemberTripMyAccountCriteria() {
        TripMyAccountCriteria criteria = new TripMyAccountCriteria();
        criteria.setUserId(USER_ID);
        criteria.setSkip(0L);
        criteria.setLimit(10L);
        criteria.setSort(ContentSortEnum.RATING);
        Calendar begin = Calendar.getInstance();
        begin.set(2013, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2013, Calendar.DECEMBER, 1);
        SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
        criteria.setPeriod(period);
        criteria.setLocale(Locale.ENGLISH);
        criteria.setRelation(RelationEnum.MEMBER);
        return new Object[][]{new Object[]{criteria}};
    }

}
