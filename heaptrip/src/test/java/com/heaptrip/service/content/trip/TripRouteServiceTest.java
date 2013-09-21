package com.heaptrip.service.content.trip;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.trip.Route;
import com.heaptrip.domain.service.content.trip.TripRouteService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripRouteServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "0";

	private static Locale LOCALE = Locale.ENGLISH;

	@Autowired
	private TripRouteService tripRouteService;

	private Route route = null;

	@Test(priority = 1, enabled = true)
	public void getRoute() {
		// call
		route = tripRouteService.getRoute(TRIP_ID, LOCALE);
		// check
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getId());
		Assert.assertNotNull(route.getText());
	}

	@Test(priority = 2, enabled = true)
	public void updateRoute() {
		// call
		String text = "test route description";
		route.getText().setValue(text, LOCALE);
		String map = "google map";
		route.setMap(map);
		tripRouteService.updateRoute(TRIP_ID, route, LOCALE);
		// check
		route = tripRouteService.getRoute(TRIP_ID, LOCALE);
		Assert.assertNotNull(route);
		Assert.assertNotNull(route.getId());
		Assert.assertNotNull(route.getText());
		Assert.assertNotNull(route.getText().getValue(LOCALE));
		Assert.assertEquals(route.getText().getValue(LOCALE), text);
		Assert.assertNotNull(route.getMap());
		Assert.assertEquals(route.getMap(), map);
	}

}
