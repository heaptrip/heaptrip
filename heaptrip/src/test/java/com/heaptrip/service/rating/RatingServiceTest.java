package com.heaptrip.service.rating;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.post.Post;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.domain.service.trip.TripUserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RatingServiceTest extends AbstractTestNGSpringContextTests {

	private static final String OWNER_ID = "ACCOUNT_FOR_" + RatingServiceTest.class.getName();

	private static final String POST_ID = "POST_FOR_" + RatingServiceTest.class.getName();

	private static final String TRIP_ID = "TRIP_FOR_" + RatingServiceTest.class.getName();

	private static final String TRIP_TABLE_ID = "1";

	private static final String USER_ID = "1";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private TripUserService tripUserService;

	@BeforeClass
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();

		Account account = new User();
		account.setId(OWNER_ID);
		account.setRating(ratingService.getDefaultAccountRating());
		accountRepository.save(account);

		Content post = new Post();
		post.setId(POST_ID);
		post.setCreated(new Date());
		ContentOwner owner = new ContentOwner();
		owner.setId(OWNER_ID);
		post.setOwner(owner);
		post.setRating(ratingService.getDefaultContentRating());
		contentRepository.save(post);

		Trip trip = new Trip();
		trip.setId(TRIP_ID);
		trip.setCreated(new Date());
		TableItem[] table = new TableItem[1];
		TableItem item = new TableItem();
		item.setId(TRIP_TABLE_ID);
		item.setEnd(new Date());
		table[0] = item;
		trip.setTable(table);
		tripRepository.save(trip);
	}

	@AfterClass(alwaysRun = true)
	public void afterTest() {
		accountRepository.remove(OWNER_ID);
		contentRepository.remove(POST_ID);
		tripRepository.remove(TRIP_ID);
		tripUserService.removeTripMembers(TRIP_ID);
		ratingRepository.removeByTargetId(OWNER_ID);
		ratingRepository.removeByTargetId(POST_ID);
	}

	@Test(enabled = true, priority = 0)
	public void canSetRatingForPost() {
		boolean can = ratingService.canSetRating(ContentEnum.POST, POST_ID, USER_ID);
		Assert.assertEquals(can, true);
	}

	@Test(enabled = true, priority = 0)
	public void canNotSetRatingForQA() {
		boolean can = ratingService.canSetRating(ContentEnum.QA, POST_ID, USER_ID);
		Assert.assertEquals(can, false);
	}

	@Test(enabled = true, priority = 0)
	public void canSetRatingForTrip() {
		// if user is not accepted trip member then he can not set rating
		boolean can = ratingService.canSetRating(ContentEnum.TRIP, TRIP_ID, USER_ID);
		Assert.assertEquals(can, false);
		TableUser tableUser = tripUserService.addTableUser(TRIP_ID, TRIP_TABLE_ID, USER_ID);
		can = ratingService.canSetRating(ContentEnum.TRIP, TRIP_ID, USER_ID);
		Assert.assertEquals(can, false);
		tripUserService.acceptTableUser(tableUser.getId());
		can = ratingService.canSetRating(ContentEnum.TRIP, TRIP_ID, USER_ID);
		Assert.assertEquals(can, true);
		// trip can be rated in six months with the launch of the last schedule
		Trip trip = tripRepository.findOne(TRIP_ID);
		TableItem item = trip.getTable()[0];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(item.getEnd());
		calendar.add(Calendar.MONTH, -7);
		item.setEnd(calendar.getTime());
		tripRepository.save(trip);
		can = ratingService.canSetRating(ContentEnum.TRIP, TRIP_ID, USER_ID);
		Assert.assertEquals(can, false);
	}

	@Test(enabled = true, priority = 1)
	public void addContentRatingForPost() throws InterruptedException, ExecutionException {
		for (int i = 1; i <= 50; i++) {
			String userId = Integer.toString(i);
			// call
			Future<ContentRating> res = ratingService.addContentRating(POST_ID, userId, ratingService.starsToRating(5));
			ContentRating contentRating = res.get();
			// check count of ratings
			int count = contentRating.getCount();
			Assert.assertEquals(count, i);
			// check content rating value
			double stars = ratingService.ratingToStars(contentRating.getValue());
			stars = Math.round(stars);
			if (i < 6) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 15) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 29) {
				Assert.assertTrue(stars >= 4 && stars < 5);
			} else {
				Assert.assertTrue(stars >= 5);
			}

			// check account rating value
			Account account = accountRepository.findOne(OWNER_ID);
			AccountRating accountRating = account.getRating();
			Assert.assertNotNull(accountRating);

			count = accountRating.getCount();
			Assert.assertEquals(count, i);

			stars = ratingService.ratingToStars(accountRating.getValue());
			stars = Math.round(stars);
			if (i < 11) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 29) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 60) {
				Assert.assertTrue(stars >= 4 && stars < 5);
			} else {
				Assert.assertTrue(stars >= 5);
			}
		}
	}

	@Test(enabled = true, priority = 2)
	public void canNotSetRatingForPost() {
		boolean can = ratingService.canSetRating(ContentEnum.POST, POST_ID, USER_ID);
		Assert.assertEquals(can, false);
	}

	@Test(enabled = true, priority = 3)
	public void addAccountRating() throws InterruptedException, ExecutionException {
		// reset account rating value
		Account account = accountRepository.findOne(OWNER_ID);
		account.setRating(ratingService.getDefaultAccountRating());
		accountRepository.save(account);
		ratingRepository.removeByTargetId(OWNER_ID);
		// add ratings
		for (int i = 1; i <= 70; i++) {
			String userId = Integer.toString(i);
			// call
			Future<AccountRating> res = ratingService
					.addAccountRating(OWNER_ID, userId, ratingService.starsToRating(5));
			AccountRating accountRating = res.get();
			// check count of ratings
			int count = accountRating.getCount();
			Assert.assertEquals(count, i);
			// check account rating value
			double stars = ratingService.ratingToStars(accountRating.getValue());
			stars = Math.round(stars);
			if (i < 11) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 29) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 60) {
				Assert.assertTrue(stars >= 4 && stars < 5);
			} else {
				Assert.assertTrue(stars >= 5);
			}
		}
	}

	@Test(enabled = true, priority = 4)
	public void addManyAccountRating() {
		// read current count of rating objects
		long count = ratingRepository.getCountByTargetId(OWNER_ID);
		// set max count of stored ratings
		Account account = accountRepository.findOne(OWNER_ID);
		account.getRating().setCount(1000);
		accountRepository.save(account);
		// add rating value
		ratingService.addAccountRating(OWNER_ID, "123", ratingService.starsToRating(5));
		// count of rating objects must not be change
		Assert.assertEquals(ratingRepository.getCountByTargetId(OWNER_ID), count);
	}

	@Test(enabled = true, priority = 5)
	public void canSetRatingForOldContent() {
		// set old date for content
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Content content = contentRepository.findOne(POST_ID);
		content.setCreated(calendar.getTime());
		// check
		boolean can = ratingService.canSetRating(ContentEnum.POST, POST_ID, USER_ID);
		Assert.assertEquals(can, false);
	}
}
