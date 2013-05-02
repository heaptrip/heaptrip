package com.heaptrip.service.trip;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.util.RandomUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "1";

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@DataProvider(name = "tripCriteria")
	public Object[][] createTripCriteria() {
		TripCriteria tripCriteria = new TripCriteria();
		tripCriteria.setOwnerId(InitTripTest.OWNER_ID);
		tripCriteria.setCategoryIds(new String[] { "1.2" });
		tripCriteria.setSkip(0L);
		tripCriteria.setLimit(InitTripTest.TRIPS_COUNT);
		tripCriteria.setSort(ContentSortEnum.CREATED);
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 11, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		tripCriteria.setPeriod(period);
		return new Object[][] { new Object[] { tripCriteria } };
	}

	@DataProvider(name = "tripWithTable")
	private Object[][] getTripWithTable() {
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

	@Test(dataProvider = "tripCriteria", enabled = false)
	public void getTripsByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		Assert.assertEquals(trips.size(), InitTripTest.TRIPS_COUNT);
	}

	@Test(dataProvider = "tripCriteria", enabled = false)
	public void getTripsCountByCriteria(TripCriteria tripCriteria) {
		Long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertNotNull(count);
		Assert.assertTrue(count.equals(InitTripTest.TRIPS_COUNT / 2L));
	}

	@Test(dataProvider = "tripWithTable", enabled = false)
	public void getNearTableItem(Trip trip) {
		TableItem item = tripService.getNearTableItem(trip);
		Assert.assertNotNull(item);
		for (TableItem ti : trip.getTable()) {
			Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
		}
	}

	@Test(dataProvider = "tripWithTable", enabled = false)
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
				Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
			}
		}
	}

	@Test(enabled = true)
	public void removeTrip() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNull(trip.getDeleted());
		tripService.removeTrip(TRIP_ID, InitTripTest.OWNER_ID);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getDeleted());
	}
}
