package com.heaptrip.service.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.FavoriteContent;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.service.trip.TripDataProvider;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TRIP_ID = InitContentTest.TRIP_ID;

	private static final String OWNER_ID = InitContentTest.OWNER_ID;

	private static final String USER_ID = InitContentTest.USER_ID;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private TripService tripService;

	@Test(priority = 1, enabled = true)
	public void setContentStatus() {
		// call
		Content content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.DRAFT);
		contentService.setContentStatus(TRIP_ID, OWNER_ID, ContentStatusEnum.PUBLISHED_ALL);
		// check
		content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.PUBLISHED_ALL);
	}

	@Test(priority = 2, enabled = true)
	public void incContentViews() {
		// call
		Content content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		long views = content.getViews().longValue();
		contentService.incContentViews(TRIP_ID);
		// check
		content = contentRepository.findOne(TRIP_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		Assert.assertEquals(content.getViews().longValue(), ++views);
	}

	@Test(priority = 3, enabled = true, dataProvider = "myAccountFavoritesTripCriteria", dataProviderClass = TripDataProvider.class)
	public void addFavoriteContent(TripMyAccountCriteria myAccountTripCriteria) {
		// call
		contentService.addFavoriteContent(TRIP_ID, ContentEnum.TRIP, USER_ID);
		// check
		myAccountTripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByMyAccountTripCriteria(myAccountTripCriteria);
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 4, enabled = true)
	public void isFavoriteContent() {
		// call
		boolean isFavorite = contentService.isFavoriteContent(TRIP_ID, USER_ID);
		// check
		Assert.assertTrue(isFavorite);
	}

	@Test(priority = 5, enabled = true)
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

	@Test(priority = 6, enabled = true, dataProvider = "myAccountFavoritesTripCriteria", dataProviderClass = TripDataProvider.class)
	public void removeFavoriteContent(TripMyAccountCriteria myAccountTripCriteria) {
		// call
		contentService.removeFavoriteContent(TRIP_ID, USER_ID);
		// check
		myAccountTripCriteria.setUserId(USER_ID);
		long count = tripService.getTripsCountByMyAccountTripCriteria(myAccountTripCriteria);
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
