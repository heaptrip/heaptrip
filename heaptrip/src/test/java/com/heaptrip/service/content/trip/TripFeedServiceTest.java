package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.content.FavoriteContentService;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.TripUserService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripFeedServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TripFeedService tripFeedService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TripUserService tripUserService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FavoriteContentService favoriteContentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeClass
    public void beforeClass() {
        // create test trip
        contentRepository.save(TripFeedDataProvider.getTrip());
        // create test users
        User[] users = TripFeedDataProvider.getUsers();
        for (User user : users) {
            accountRepository.save(user);
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // remove test trip
        contentRepository.remove(TripFeedDataProvider.CONTENT_ID);
        // remove test users
        User[] users = TripFeedDataProvider.getUsers();
        AccountNotificationCriteria criteria = new AccountNotificationCriteria();
        List<Notification> notifications = new ArrayList<>();
        for (User user : users) {
            accountRepository.remove(user);

            criteria.setAccountId(user.getId());
            notifications.addAll(notificationService.findByUserNotificationCriteria(criteria));
        }
        // remove test members
        tripUserService.removeTripMembers(TripFeedDataProvider.CONTENT_ID);
        //remove favorites
        favoriteContentService.removeFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID);

        for (Notification notification : notifications) {
            notificationRepository.remove(notification.getId());
        }
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "feedCriteria")
    public void getContentsByFeedCriteria(TripFeedCriteria feedCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByFeedCriteria(feedCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertNotNull(contents.get(0));
        Assert.assertEquals(contents.get(0).getId(), TripFeedDataProvider.CONTENT_ID);
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getContentsByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertNotNull(contents.get(0));
        Assert.assertEquals(contents.get(0).getId(), TripFeedDataProvider.CONTENT_ID);
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getContentsByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        // call
        List<Trip> contents = tripFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertNotNull(contents.get(0));
        Assert.assertEquals(contents.get(0).getId(), TripFeedDataProvider.CONTENT_ID);
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "feedCriteria")
    public void getCountByFeedCriteria(TripFeedCriteria feedCriteria) {
        // call
        long count = tripFeedService.getCountByFeedCriteria(feedCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getCountByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        // call
        long count = tripFeedService.getCountByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(enabled = true, dataProviderClass = TripFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getCountByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        // call
        long count = tripFeedService.getCountByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(enabled = true, dataProvider = "memberMyAccountCriteria", dataProviderClass = TripFeedDataProvider.class)
    public void getContentsByMemberCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
        // prepare
        TripUser user = tripUserService.sendInvite(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.TABLE_IDs[1], TripFeedDataProvider.USER_ID);
        tripUserService.acceptTripMember(user.getId());
        // call
        List<Trip> trips = tripFeedService.getContentsByMyAccountCriteria(tripMyAccountCriteria);
        // check
        Assert.assertNotNull(trips);
        Assert.assertEquals(trips.size(), 1);
        // remove test data
        tripUserService.removeTripMember(user.getId());
    }

    @Test(enabled = true, dataProvider = "memberMyAccountCriteria", dataProviderClass = TripFeedDataProvider.class)
    public void getCountByMemberCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
        // prepare
        TripUser user = tripUserService.sendInvite(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.TABLE_IDs[1], TripFeedDataProvider.USER_ID);
        tripUserService.acceptTripMember(user.getId());
        // call
        long count = tripFeedService.getCountByMyAccountCriteria(tripMyAccountCriteria);
        // check
        Assert.assertNotNull(count);
        Assert.assertEquals(count, 1);
        // remove test data
        tripUserService.removeTripMember(user.getId());
    }

    @Test(enabled = true, dataProvider = "favoriteMyAccountCriteria", dataProviderClass = TripFeedDataProvider.class)
    public void getContentsByFavoriteCriteria(TripMyAccountCriteria tripMyAccountCriteria) throws ExecutionException, InterruptedException {
        // prepare
        favoriteContentService.addFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID).get();
        // check that twice can not be added
        favoriteContentService.addFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID).get();
        // call
        List<Trip> trips = tripFeedService.getContentsByMyAccountCriteria(tripMyAccountCriteria);
        // check
        Assert.assertNotNull(trips);
        Assert.assertEquals(trips.size(), 1);
        // remove test data
        favoriteContentService.removeFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID);
    }

    @Test(enabled = true, dataProvider = "favoriteMyAccountCriteria", dataProviderClass = TripFeedDataProvider.class)
    public void getCountByFavoriteCriteria(TripMyAccountCriteria tripMyAccountCriteria) throws ExecutionException, InterruptedException {
        // prepare
        favoriteContentService.addFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID).get();
        // check that twice can not be added
        favoriteContentService.addFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID).get();
        // call
        long count = tripFeedService.getCountByMyAccountCriteria(tripMyAccountCriteria);
        // check
        Assert.assertNotNull(count);
        Assert.assertEquals(count, 1);
        // remove test data
        favoriteContentService.removeFavorites(TripFeedDataProvider.CONTENT_ID, TripFeedDataProvider.USER_ID);
    }
}
