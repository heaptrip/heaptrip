package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import com.heaptrip.util.RandomUtils;
import org.testng.annotations.DataProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventDataProvider {

    static String OWNER_ID = "OWNER_FOR_EVENT_SERVICES_TESTS";

    static String USER_ID = "USER_FOR_EVENT_SERVICES_TESTS";

    static String CONTENT_ID = "CONTENT_FOR_EVENT_SERVICES_TESTS";

    static String[] CATEGORY_IDS = new String[]{"1.2", "1.3"};

    static String[] EVENT_TYPE_IDS = new String[]{"1", "2", "3"};

    @DataProvider(name = "memberMyAccountCriteria")
    public static Object[][] getMemberTripMyAccountCriteria() {
        TripMyAccountCriteria criteria = new TripMyAccountCriteria();
        criteria.setUserId(USER_ID);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, new String[]{CATEGORY_IDS[0]}));
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

    @DataProvider(name = "tripWithTable")
    private static Object[][] getTripWithTable() {
        int tableSize = RandomUtils.getRandomInt(1, 10);
        TableItem[] table = new TableItem[tableSize];
        for (int j = 0; j < tableSize; j++) {
            TableItem item = new TableItem();
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
            item.setId(Integer.toString(j));
            table[j] = item;
        }
        Trip trip = new Trip();
        trip.setTable(table);
        return new Object[][]{new Object[]{trip}};
    }
}
