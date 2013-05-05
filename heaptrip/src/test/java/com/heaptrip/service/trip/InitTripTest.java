package com.heaptrip.service.trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.entity.ContentOwner;
import com.heaptrip.domain.entity.ContentRegion;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.util.RandomUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitTripTest extends AbstractTestNGSpringContextTests {

	static long TRIPS_COUNT = 10;

	static String OWNER_ID = "1";

	static String ALL_USERS = "0";

	static String USER_ID = "2";

	static String[] CATEGORY_IDS = new String[] { "1.2", "1.3" };

	static String[] REGION_IDS = new String[] { "1", "2", "3" };

	private List<Trip> trips = null;

	@Autowired
	private TripService tripService;

	private TableItem[] getRandomTable() {
		int tableSize = RandomUtils.getRandomInt(1, 10);
		TableItem[] table = new TableItem[tableSize];
		for (int j = 0; j < tableSize; j++) {
			TableItem item = new TableItem();
			item.setId(Integer.toString(j));
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
		return table;
	}

	private void initTrips() {
		trips = new ArrayList<>();
		Locale locale = Locale.ENGLISH;
		for (int i = 0; i < TRIPS_COUNT; i++) {
			Trip trip = new Trip();
			trip.setId(Integer.toString(i));
			trip.setOwner(new ContentOwner(OWNER_ID));
			trip.setCategories(new ContentCategory[] { new ContentCategory(CATEGORY_IDS[0]),
					new ContentCategory(CATEGORY_IDS[1]) });
			trip.setRegions(new ContentRegion[] { new ContentRegion(REGION_IDS[0]), new ContentRegion(REGION_IDS[1]),
					new ContentRegion(REGION_IDS[2]) });
			trip.setName(new MultiLangText("my name No " + Integer.toString(i), locale));
			trip.setSummary(new MultiLangText("my summary", locale));
			trip.setDescription(new MultiLangText("my description", locale));
			trip.setSummary(new MultiLangText("my summary", locale));
			trip.setLangs(new String[] { locale.getLanguage() });
			trip.setTable(getRandomTable());
			trip.setAllowed(new String[] { ALL_USERS, USER_ID });
			trips.add(trip);
		}
	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		initTrips();
		for (Trip trip : trips) {
			tripService.saveTrip(trip);
		}
	}

	@AfterTest
	public void afterTest() {
		for (Trip trip : trips) {
			// tripService.hardRemoveTrip(trip.getId());
		}
	}
}
