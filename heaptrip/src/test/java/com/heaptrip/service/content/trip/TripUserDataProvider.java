package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.util.RandomUtils;

import java.util.Calendar;
import java.util.Date;

public class TripUserDataProvider {

    static String TRIP_ID = "tripId4TripUserServiceTest";

    static String[] TABLE_IDs = new String[]{"0", "1", "2", "3", "4"};

    static String OWNER_ID = "ownerId4TripUserServiceTest";

    static String[] USER_IDs = new String[]{"user1Id4TripUserServiceTest", "user2Id4TripUserServiceTest", "user3Id4TripUserServiceTest"};

    static String USER_EMAIL = "test@test.test";

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
        trip.setId(TRIP_ID);
        trip.setOwnerId(OWNER_ID);
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
        trip.setStatus(status);
        trip.setTable(getTable());
        return trip;
    }
}
