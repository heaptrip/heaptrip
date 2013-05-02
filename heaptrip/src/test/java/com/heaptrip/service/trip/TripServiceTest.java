package com.heaptrip.service.trip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private TripService tripService;

	@DataProvider(name = "tripCriteria")
	public Object[][] createTripCriteria() {
		TripCriteria tripCriteria = new TripCriteria();
		tripCriteria.setCategoryIds(new String[] { "1.2" });
		tripCriteria.setSkip(5L);
		tripCriteria.setLimit(5L);
		tripCriteria.setSort(ContentSortEnum.CREATED);
		tripCriteria.setOwnerId(InitTripTest.OWNER_ID);
		return new Object[][] { new Object[] { tripCriteria } };
	}

	@Test(dataProvider = "tripCriteria", enabled = false)
	public void getTripsByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		Assert.assertEquals(trips.size(), 5L);
	}

	@Test(dataProvider = "tripCriteria", enabled = false)
	public void getTripsCountByCriteria(TripCriteria tripCriteria) {
		Long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertNotNull(count);
		Assert.assertTrue(count.equals(5L));
	}
}
