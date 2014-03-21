package com.heaptrip.service.content.trip;

import com.heaptrip.domain.service.content.trip.TripRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripRouteServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripRouteService tripRouteService;

}
