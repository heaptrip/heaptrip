package com.heaptrip.service.content;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitContentTest extends AbstractTestNGSpringContextTests {

	static String[] CATEGORY_IDS = new String[] { "2.4.7", "3.2" };

	static String TRIP_ID = InitContentTest.class.getName();

	static String OWNER_ID = "1";

	static String USER_ID = "1";

	@Autowired
	private TripService tripService;

	@Autowired
	private RegionService regionService;

	private Trip trip = null;

	Locale locale = Locale.ENGLISH;

	private ContentCategory[] getCategories() {
		return new ContentCategory[] { new ContentCategory(CATEGORY_IDS[0]), new ContentCategory(CATEGORY_IDS[1]) };
	}

	@BeforeTest()
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
		trip = new Trip();
		trip.setId(TRIP_ID);
		trip.setOwner(new ContentOwner(OWNER_ID));
		trip.setName(new MultiLangText("test name", locale));
		trip.setSummary(new MultiLangText("test summary", locale));
		trip.setDescription(new MultiLangText("test description", locale));
		trip.setCategories(getCategories());
		tripService.saveTrip(trip, locale);
	}

	@AfterTest
	public void afterTest() {
		if (trip != null) {
			tripService.hardRemoveTrip(trip.getId());
		}
	}
}
