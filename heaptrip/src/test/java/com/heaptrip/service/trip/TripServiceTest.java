package com.heaptrip.service.trip;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "1";

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@Test(dataProvider = "tripCriteriaForFeed", dataProviderClass = TripDataProvider.class, enabled = true, priority = 1)
	public void getTripsForFeedByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteriaForMyAccount", dataProviderClass = TripDataProvider.class, enabled = true, priority = 2)
	public void getTripsForMyAccountCountByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteriaForNotMyAccount", dataProviderClass = TripDataProvider.class, enabled = true, priority = 3)
	public void getTripsForNotMyAccountCountByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteriaForFeed", dataProviderClass = TripDataProvider.class, enabled = true, priority = 4)
	public void getCountForFeedByCriteria(TripCriteria tripCriteria) {
		long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertNotNull(count);
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteriaForMyAccount", dataProviderClass = TripDataProvider.class, enabled = true, priority = 5)
	public void getCountForMyAccountByCriteria(TripCriteria tripCriteria) {
		long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertNotNull(count);
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteriaForNotMyAccount", dataProviderClass = TripDataProvider.class, enabled = true, priority = 6)
	public void getCountForNotMyAccountByCriteria(TripCriteria tripCriteria) {
		long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertNotNull(count);
		Assert.assertEquals(count, InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripWithTable", dataProviderClass = TripDataProvider.class, enabled = true, priority = 7)
	public void getNearTableItem(Trip trip) {
		TableItem item = tripService.getNearTableItem(trip);
		Assert.assertNotNull(item);
		for (TableItem ti : trip.getTable()) {
			Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
		}
	}

	@Test(dataProvider = "tripWithTable", dataProviderClass = TripDataProvider.class, enabled = true, priority = 8)
	public void getNearTableItemByPeriod(Trip trip) {
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 6, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		TableItem item = tripService.getNearTableItemByPeriod(trip, period);
		if (item != null) {
			Assert.assertTrue(item.getBegin().after(period.getDateBegin())
					&& item.getBegin().before(period.getDateEnd()));
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
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNull(trip.getDeleted());
		Assert.assertNotNull(trip.getAllowed());
		tripService.removeTrip(TRIP_ID, InitTripTest.OWNER_ID);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getDeleted());
		Assert.assertNull(trip.getAllowed());
	}

	@Test(enabled = true, priority = 10)
	public void setTripStatus() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getStatus());
		Assert.assertNotNull(trip.getStatus().getValue());
		Assert.assertEquals(trip.getStatus().getValue(), ContentStatusEnum.DRAFT);
		tripService.setTripStatus(TRIP_ID, InitTripTest.OWNER_ID, ContentStatusEnum.PUBLISHED_ALL);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getStatus());
		Assert.assertNotNull(trip.getStatus().getValue());
		Assert.assertEquals(trip.getStatus().getValue(), ContentStatusEnum.PUBLISHED_ALL);
	}

	@Test(enabled = true, priority = 11)
	public void incTripViews() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getViews());
		long views = trip.getViews().longValue();
		tripService.incTripViews(TRIP_ID);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getViews());
		Assert.assertEquals(trip.getViews().longValue(), ++views);
	}

	@Test(enabled = true, priority = 12)
	public void getTripInfo() {
		Trip trip = tripService.getTripInfo(TRIP_ID, Locale.ENGLISH);
		Assert.assertNotNull(trip);
	}

	@Test(enabled = true, priority = 13)
	public void updateTripInfo() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		MultiLangText text = new MultiLangText("test description", Locale.ENGLISH);
		trip.setDescription(text);
		tripService.updateTripInfo(trip, Locale.ENGLISH);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getDescription());
		Assert.assertEquals(trip.getDescription(), text);
	}
}
