package com.heaptrip.service.content;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TRIP_ID = InitContentTest.TRIP_ID;

	private static final String USER_ID = InitContentTest.USER_ID;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private TripService tripService;

	@Test(priority = 1, enabled = true)
	public void setStatus() {
		// call
		Content content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.DRAFT);
		contentService.setStatus(TRIP_ID, ContentStatusEnum.PUBLISHED_ALL);
		// check
		content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.PUBLISHED_ALL);
	}

	@Test(priority = 2, enabled = true)
	public void incViews() throws InterruptedException, ExecutionException {
		String userId = "123";
		String ip = "127.0.0.1";
		Content content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertTrue(content.getViews() == null || content.getViews().getCount() == 0);
		// call
		Future<Void> views = contentService.incViews(TRIP_ID, userId);
		views.get();
		views = contentService.incViews(TRIP_ID, userId);
		views.get();
		views = contentService.incViews(TRIP_ID, ip);
		views.get();
		views = contentService.incViews(TRIP_ID, ip);
		views.get();
		// check
		content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		Assert.assertNotNull(content.getViews().getCount());
		Assert.assertEquals(content.getViews().getCount(), 2);
	}

	@Test(priority = 3, enabled = true, dataProvider = "favoritesTripMyAccountCriteria", dataProviderClass = ContentDataProvider.class)
	public void addFavorites(TripMyAccountCriteria myAccountTripCriteria) {
		// call
		contentService.addFavorites(TRIP_ID, USER_ID);
		// check that twice can not be added
		contentService.addFavorites(TRIP_ID, USER_ID);
		// check
		myAccountTripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByTripMyAccountCriteria(myAccountTripCriteria);
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 4, enabled = true)
	public void isFavorites() {
		// call
		boolean isFavorite = contentService.isFavorites(TRIP_ID, USER_ID);
		// check
		Assert.assertTrue(isFavorite);
	}

	@Test(priority = 5, enabled = true)
	public void getFavoritesContents() {
		// call
		List<Content> list = contentService.getFavoritesContents(ContentEnum.TRIP, USER_ID,
				LanguageUtils.getEnglishLocale());
		// check
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		// call
		list = contentService.getFavoritesContents(USER_ID, LanguageUtils.getEnglishLocale());
		// check
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 6, enabled = true, dataProvider = "favoritesTripMyAccountCriteria", dataProviderClass = ContentDataProvider.class)
	public void removeFavorites(TripMyAccountCriteria myAccountTripCriteria) {
		// call
		contentService.removeFavorites(TRIP_ID, USER_ID);
		// check
		myAccountTripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByTripMyAccountCriteria(myAccountTripCriteria);
		Assert.assertEquals(count, 0);
	}

	@Test(priority = 7, enabled = true, dataProvider = "feedCriteria", dataProviderClass = ContentDataProvider.class)
	public void getContentsByCriteria(FeedCriteria feedCriteria) {
		// call
		List<Content> content = contentService.getContentsByFeedCriteria(feedCriteria);
		// check
		Assert.assertNotNull(content);
		Assert.assertTrue(content.size() > 0);
	}

	@Test(priority = 8, enabled = true, dataProvider = "feedCriteria", dataProviderClass = ContentDataProvider.class)
	public void getCountByFeedCriteria(FeedCriteria feedCriteria) {
		// call
		long count = contentService.getContentsCountByFeedCriteria(feedCriteria);
		// check
		Assert.assertTrue(count > 0);
	}
}
