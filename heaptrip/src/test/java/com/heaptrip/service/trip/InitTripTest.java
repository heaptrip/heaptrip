package com.heaptrip.service.trip;

import java.util.ArrayList;
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
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitTripTest extends AbstractTestNGSpringContextTests {

	static int TRIPS_COUNT = 10;

	static String OWNER_ID = "1";

	private List<Trip> trips = null;

	@Autowired
	private TripService tripService;

	private void init() {
		trips = new ArrayList<>();
		Locale locale = Locale.ENGLISH;

		for (int i = 0; i < TRIPS_COUNT; i++) {
			Trip trip = new Trip();
			trip.setId(Integer.toString(i));
			trip.setOwner(new ContentOwner(OWNER_ID));
			trip.setCategories(new ContentCategory[] { new ContentCategory("1.2"), new ContentCategory("1.3") });
			trip.setRegions(new ContentRegion[] { new ContentRegion("1"), new ContentRegion("2"),
					new ContentRegion("3") });
			trip.setName(new MultiLangText("my name No " + Integer.toString(i), locale));
			trip.setSummary(new MultiLangText("my summary", locale));
			trip.setDescription(new MultiLangText("my description", locale));
			trip.setSummary(new MultiLangText("my summary", locale));
			trip.setLangs(new String[] { locale.getLanguage() });
			trips.add(trip);
		}
	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		init();
		for (Trip trip : trips) {
			tripService.saveTrip(trip);
		}
	}

	@AfterTest
	public void afterTest() {
		for (Trip trip : trips) {
			tripService.hardRemoveTrip(trip.getId());
		}
	}
}
