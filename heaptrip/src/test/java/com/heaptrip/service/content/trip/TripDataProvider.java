package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.util.RandomUtils;
import org.testng.annotations.DataProvider;

import java.util.Calendar;
import java.util.Date;

public class TripDataProvider {

    static String OWNER_ID = "OWNER_FOR_TRIP_SERVICES_TESTS";

    static String USER_ID = "USER_FOR_TRIP_SERVICES_TESTS";

    static String[] CONTENT_IDS = new String[]{"0", "1", "2", "3", "4"};

    static String[] CATEGORY_IDS = new String[]{"1.2", "1.3"};

    static String[] REGION_IDS = null;

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
