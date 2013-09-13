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
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.domain.service.rating.RatingService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RatingServiceTest extends AbstractTestNGSpringContextTests {

	private static final String OWNER_ID = "ACCOUNT_FOR_" + RatingServiceTest.class.getName();

	private static final String CONTENT_ID = "CONTENT_FOR_" + RatingServiceTest.class.getName();

	private static final String USER_ID = "1";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private RatingService ratingService;

	@BeforeClass
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();

		Account account = new User();
		account.setId(OWNER_ID);
		account.setRating(ratingService.getDefaultAccountRating());
		accountRepository.save(account);

		Content content = new Content();
		content.setId(CONTENT_ID);
		content.setCreated(new Date());
		ContentOwner owner = new ContentOwner();
		owner.setId(OWNER_ID);
		content.setOwner(owner);
		content.setRating(ratingService.getDefaultContentRating());
		contentRepository.save(content);
	}

	@AfterClass
	public void afterTest() {
		accountRepository.remove(OWNER_ID);
		contentRepository.remove(CONTENT_ID);
		ratingRepository.removeByTargetId(OWNER_ID);
		ratingRepository.removeByTargetId(CONTENT_ID);
	}

	@Test(enabled = false, priority = 0)
	public void canSetRating() {
		boolean can = ratingService.canSetRating(ContentEnum.POST, CONTENT_ID, USER_ID);
		Assert.assertEquals(can, true);
	}

	@Test(enabled = false, priority = 1)
	public void addContentRating() throws InterruptedException, ExecutionException {
		for (int i = 1; i <= 50; i++) {
			String userId = Integer.toString(i);
			// call
			Future<ContentRating> res = ratingService.addContentRating(CONTENT_ID, userId,
					ratingService.starsToRating(5));
			ContentRating contentRating = res.get();
			// check count of ratings
			int count = contentRating.getCount();
			Assert.assertEquals(count, i);
			// check content rating value
			double stars = ratingService.ratingToStars(contentRating.getValue());
			if (i < 6) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 13) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 23) {
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
			if (i < 12) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 26) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 46) {
				Assert.assertTrue(stars >= 4 && stars < 5);
			} else {
				Assert.assertTrue(stars >= 5);
			}
		}
	}

	@Test(enabled = false, priority = 2)
	public void canNotSetRating() {
		boolean can = ratingService.canSetRating(ContentEnum.POST, CONTENT_ID, USER_ID);
		Assert.assertEquals(can, false);
	}

	@Test(enabled = false, priority = 3)
	public void addAccountRating() throws InterruptedException, ExecutionException {
		// reset account rating value
		Account account = accountRepository.findOne(OWNER_ID);
		account.setRating(ratingService.getDefaultAccountRating());
		accountRepository.save(account);
		// ad ratings
		for (int i = 1; i <= 50; i++) {
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
			if (i < 12) {
				Assert.assertTrue(stars >= 2 && stars < 3);
			} else if (i < 26) {
				Assert.assertTrue(stars >= 3 && stars < 4);
			} else if (i < 46) {
				Assert.assertTrue(stars >= 4 && stars < 5);
			} else {
				Assert.assertTrue(stars >= 5);
			}
		}
	}

	@Test(enabled = false, priority = 4)
	public void addManyAccountRating() {
		// read current count of rating objects
		long count = ratingRepository.getCountByTargetId(OWNER_ID);
		// set max count of stored ratings
		Account account = accountRepository.findOne(CONTENT_ID);
		account.getRating().setCount(1000);
		accountRepository.save(account);
		// add rating value
		ratingService.addAccountRating(OWNER_ID, "123", ratingService.starsToRating(5));
		// count of rating objects must not be change
		Assert.assertEquals(ratingRepository.getCountByTargetId(OWNER_ID), count);
	}

	@Test(enabled = false, priority = 5)
	public void canSetRatingForOldContent() {
		// set old date for content
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Content content = contentRepository.findOne(CONTENT_ID);
		content.setCreated(calendar.getTime());
		// check
		boolean can = ratingService.canSetRating(ContentEnum.POST, CONTENT_ID, USER_ID);
		Assert.assertEquals(can, false);
	}
}
