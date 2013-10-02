package com.heaptrip.service.content.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.heaptrip.domain.service.content.trip.TripRouteService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripRouteServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private TripRouteService tripRouteService;

}
