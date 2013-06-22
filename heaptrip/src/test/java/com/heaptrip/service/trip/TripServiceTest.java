package com.heaptrip.service.trip;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;
import com.heaptrip.domain.service.trip.MyAccountTripCriteria;
import com.heaptrip.domain.service.trip.SearchPeriod;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "1";

	private static String DELETED_TRIP_ID = "2";

	private static String TABLE_ID = "0";

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@Test(dataProvider = "feedTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 1)
	public void getTripsByFeedTripCriteria(FeedTripCriteria feedTripCriteria) {
		// call
		List<Trip> trips = tripService.getTripsByFeedTripCriteria(feedTripCriteria);
		// check
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "myAccountTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 2)
	public void getTripsByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria) {
		// call
		List<Trip> trips = tripService.getTripsByMyAccountTripCriteria(myAccountTripCriteria);
		// check
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "foreignAccountTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 3)
	public void getTripsByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria) {
		// call
		List<Trip> trips = tripService.getTripsByForeignAccountTripCriteria(foreignAccountTripCriteria);
		// check
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "feedTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 4)
	public void getTripsCountByFeedTripCriteria(FeedTripCriteria feedTripCriteria) {
		// call
		long count = tripService.getTripsCountByFeedTripCriteria(feedTripCriteria);
		// check
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "myAccountTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 5)
	public void getTripsCountByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria) {
		// call
		long count = tripService.getTripsCountByMyAccountTripCriteria(myAccountTripCriteria);
		// check
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "foreignAccountTripCriteria", dataProviderClass = TripDataProvider.class, enabled = true, priority = 6)
	public void getTripsCountByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria) {
		// call
		long count = tripService.getTripsCountByForeignAccountTripCriteria(foreignAccountTripCriteria);
		// check
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripWithTable", dataProviderClass = TripDataProvider.class, enabled = true, priority = 7)
	public void getNearTableItem(Trip trip) {
		// call
		TableItem item = tripService.getNearTableItem(trip);
		// check
		Assert.assertNotNull(item);
		for (TableItem ti : trip.getTable()) {
			Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
		}
	}

	@Test(dataProvider = "tripWithTable", dataProviderClass = TripDataProvider.class, enabled = true, priority = 8)
	public void getNearTableItemByPeriod(Trip trip) {
		// call
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 6, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		TableItem item = tripService.getNearTableItemByPeriod(trip, period);
		// check
		if (item != null && item.getBegin() != null) {
			Assert.assertTrue(item.getBegin().after(period.getDateBegin()));
			for (TableItem ti : trip.getTable()) {
				if (period.getDateBegin() != null && ti.getBegin() != null
						&& ti.getBegin().before(period.getDateBegin())) {
					continue;
				}
				Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
			}
		}
	}

	@Test(enabled = true, priority = 9)
	public void removeTrip() {
		// call
		Trip trip = tripRepository.findOne(DELETED_TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNull(trip.getDeleted());
		Assert.assertNotNull(trip.getAllowed());
		tripService.removeTrip(DELETED_TRIP_ID);
		// check
		trip = tripRepository.findOne(DELETED_TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getDeleted());
		Assert.assertTrue(ArrayUtils.isEmpty(trip.getAllowed()));
		Assert.assertEquals(trip.getStatus().getValue(), ContentStatusEnum.DELETED);
	}

	@Test(enabled = true, priority = 10)
	public void getTripInfo() {
		// call
		Trip trip = tripService.getTripInfo(TRIP_ID, Locale.ENGLISH);
		// check
		Assert.assertNotNull(trip);
	}

	@Test(enabled = true, priority = 11)
	public void updateTripInfo() {
		// call
		Locale locale = new Locale("ru");
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		String name = "Тестовая поездка No1";
		trip.getName().setValue(name, locale);
		trip.getSummary().setValue("Краткое описание тестовой поездки", locale);
		trip.getDescription().setValue("Полное описание тестовой поездки", locale);
		tripService.updateTripInfo(trip, locale);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getName());
		Assert.assertNotNull(trip.getName().getValue(locale));
		Assert.assertEquals(trip.getName().getValue(locale), name);
	}

	@Test(enabled = true, priority = 12)
	public void abortTableItem() {
		// call
		String cause = "cause interruption of travel";
		tripService.abortTableItem(TRIP_ID, TABLE_ID, cause);
		// check
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		TableItem item = trip.getTable()[0];
		Assert.assertNotNull(item.getStatus());
		Assert.assertNotNull(item.getStatus().getValue());
		Assert.assertEquals(item.getStatus().getValue(), TableStatusEnum.ABORTED);
		Assert.assertNotNull(item.getStatus().getText());
		Assert.assertEquals(item.getStatus().getText(), cause);
	}

	@Test(enabled = true, priority = 13)
	public void cancelTableItem() {
		// call
		String cause = "cause interruption of travel";
		tripService.cancelTableItem(TRIP_ID, TABLE_ID, cause);
		// check
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		TableItem item = trip.getTable()[0];
		Assert.assertNotNull(item.getStatus());
		Assert.assertNotNull(item.getStatus().getValue());
		Assert.assertEquals(item.getStatus().getValue(), TableStatusEnum.CANCELED);
		Assert.assertNotNull(item.getStatus().getText());
		Assert.assertEquals(item.getStatus().getText(), cause);
	}
}
