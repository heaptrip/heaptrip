package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripFeedServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripFeedService tripFeedService;

    @Autowired
    private ContentRepository contentRepository;

    private Trip trip = null;

    @BeforeClass
    public void init() {
        trip = new Trip();
        trip.setId(TripFeedDataProvider.CONTENT_ID);
        trip.setOwnerId(TripFeedDataProvider.OWNER_ID);
        trip.setAllowed(new String[]{TripFeedDataProvider.USER_ID});
        trip.setCategoryIds(TripFeedDataProvider.CATEGORY_IDS);
        trip.setRegionIds(TripFeedDataProvider.REGION_IDS);
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
        trip.setStatus(status);
        trip.setTable(InitTripTest.getRandomTable());
        contentRepository.save(trip);
    }

    @AfterClass(alwaysRun = true)
    public void release() {
        contentRepository.remove(TripFeedDataProvider.CONTENT_ID);
    }

    @Test(priority = 0, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "feedCriteria")
    public void getContentsByFeedCriteria(TripFeedCriteria feedCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByFeedCriteria(feedCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), trip);
    }

    @Test(priority = 1, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getContentsByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), trip);
    }

    @Test(priority = 2, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getContentsByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), trip);
    }

    @Test(priority = 3, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "feedCriteria")
    public void getCountByFeedCriteria(TripFeedCriteria feedCriteria) {
        // call
        long count = tripFeedService.getCountByFeedCriteria(feedCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(priority = 4, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getCountByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        // call
        long count = tripFeedService.getCountByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(priority = 5, enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getCountByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        // call
        long count = tripFeedService.getCountByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }
}
