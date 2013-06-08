package com.heaptrip.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.ContentOwner;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.FavoriteContent;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.ContentRepository;
import com.heaptrip.domain.service.ContentService;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.service.trip.TripDataProvider;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "11";

	static String OWNER_ID = "1";

	static String USER_ID = "1";

	private Trip trip = null;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private TripService tripService;

	@BeforeClass
	public void init() {
		trip = new Trip();
		trip.setId(TRIP_ID);
		trip.setOwner(new ContentOwner(OWNER_ID));
		trip.setName(new MultiLangText("test name", Locale.ENGLISH));
		trip.setSummary(new MultiLangText("test summary", Locale.ENGLISH));
		trip.setDescription(new MultiLangText("test description", Locale.ENGLISH));
		trip.setLangs(new String[] { Locale.ENGLISH.getLanguage() });
		tripService.saveTrip(trip);
	}

	@AfterClass
	public void destroy() {
		if (trip != null) {
			tripService.hardRemoveTrip(trip.getId());
		}
	}

	@Test(enabled = true)
	public void setTripStatus() {
		// call
		Content content = contentRepository.findById(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.DRAFT);
		contentService.setContentStatus(TRIP_ID, OWNER_ID, ContentStatusEnum.PUBLISHED_ALL);
		// check
		content = contentRepository.findById(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.PUBLISHED_ALL);
	}

	@Test(enabled = true)
	public void incTripViews() {
		// call
		Content content = contentRepository.findById(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		long views = content.getViews().longValue();
		contentService.incContentViews(TRIP_ID);
		// check
		content = contentRepository.findById(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		Assert.assertEquals(content.getViews().longValue(), ++views);
	}

	@Test(priority = 1, enabled = true, dataProvider = "favoritesCriteria", dataProviderClass = TripDataProvider.class)
	public void addFavoriteContent(TripCriteria tripCriteria) {
		// call
		contentService.addFavoriteContent(TRIP_ID, ContentEnum.TRIP, USER_ID);
		// check
		tripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 2, enabled = true)
	public void isFavoriteContent() {
		// call
		boolean isFavorite = contentService.isFavoriteContent(TRIP_ID, USER_ID);
		// check
		Assert.assertTrue(isFavorite);
	}

	@Test(priority = 3, enabled = true)
	public void getFavoriteContents() {
		// call
		List<FavoriteContent> list = contentService.getFavoriteContents(ContentEnum.TRIP, USER_ID);
		// check
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		// call
		list = contentService.getFavoriteContents(USER_ID);
		// check
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 4, enabled = true, dataProvider = "favoritesCriteria", dataProviderClass = TripDataProvider.class)
	public void removeFavoriteContent(TripCriteria tripCriteria) {
		// call
		contentService.removeFavoriteContent(TRIP_ID, USER_ID);
		// check
		tripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByCriteria(tripCriteria);
		Assert.assertEquals(count, 0);
	}
}
