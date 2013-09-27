package com.heaptrip.service.content.trip;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.TableStatusEnum;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceTest extends AbstractTestNGSpringContextTests {

	private static final String POST_ID = "POST_FOR_TRIP_SERVICE_TEST";

	private String OWNER_ID = TripDataProvider.OWNER_ID;

	private String TRIP_ID = TripDataProvider.CONTENT_IDS[1];

	private String DELETED_TRIP_ID = TripDataProvider.CONTENT_IDS[2];

	private String TABLE_ID = "0";

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private PostService postService;

	private Post post = null;

	@BeforeClass
	public void init() {
		post = new Post();
		post.setId(POST_ID);
		ContentOwner owner = new ContentOwner();
		owner.setId(OWNER_ID);
		post.setOwner(owner);
		postService.save(post, Locale.ENGLISH);
	}

	@AfterClass(alwaysRun = true)
	public void relese() {
		postService.hardRemove(POST_ID);
	}

	@Test(priority = 0, enabled = false)
	public void getTableItems() {
		// call
		List<TableItem> tableItems = tripService.getTableItems(TRIP_ID);
		// check
		Assert.assertNotNull(tableItems);
		Assert.assertTrue(tableItems.size() > 0);
	}

	@Test(priority = 1, enabled = true, dataProviderClass = TripDataProvider.class, dataProvider = "tripWithTable")
	public void getNearestTableItem(Trip trip) {
		// call
		TableItem item = tripService.getNearestTableItem(trip);
		// check
		Assert.assertNotNull(item);
		for (TableItem ti : trip.getTable()) {
			Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
		}
	}

	@Test(priority = 2, enabled = true, dataProviderClass = TripDataProvider.class, dataProvider = "tripWithTable")
	public void getNearestTableItemByPeriod(Trip trip) {
		// call
		Calendar begin = Calendar.getInstance();
		begin.set(2013, 0, 1);
		Calendar end = Calendar.getInstance();
		end.set(2013, 6, 1);
		SearchPeriod period = new SearchPeriod(begin.getTime(), end.getTime());
		TableItem item = tripService.getNearestTableItemByPeriod(trip, period);
		// check
		if (item != null && item.getBegin() != null) {
			Assert.assertTrue(item.getBegin().after(period.getDateBegin()));
			for (TableItem ti : trip.getTable()) {
				if (period.getDateBegin() != null && ti.getBegin() != null
						&& ti.getBegin().before(period.getDateBegin())) {
					continue;
				}
				Assert.assertTrue(item.equals(ti) || item.getBegin().before(ti.getBegin()));
			}
		}
	}

	@Test(priority = 3, enabled = true)
	public void getLatestTableItem() {
		// call
		TableItem item = tripService.getLatestTableItem(TRIP_ID);
		// check
		Assert.assertNotNull(item);
		Trip trip = tripRepository.findOne(TRIP_ID);
		for (TableItem ti : trip.getTable()) {
			Assert.assertTrue(item.equals(ti)
					|| (item.getEnd() != null && ti.getEnd() != null && item.getEnd().after(ti.getEnd())));
		}
	}

	@Test(priority = 4, enabled = true)
	public void remove() {
		// call
		Trip trip = tripRepository.findOne(DELETED_TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNull(trip.getDeleted());
		Assert.assertNotNull(trip.getAllowed());
		tripService.remove(DELETED_TRIP_ID);
		// check
		trip = tripRepository.findOne(DELETED_TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getDeleted());
		Assert.assertTrue(ArrayUtils.isEmpty(trip.getAllowed()));
		Assert.assertEquals(trip.getStatus().getValue(), ContentStatusEnum.DELETED);
	}

	@Test(priority = 5, enabled = true)
	public void getTripInfo() {
		// call
		Trip trip = tripService.getTripInfo(TRIP_ID, Locale.ENGLISH);
		// check
		Assert.assertNotNull(trip);
	}

	@Test(priority = 6, enabled = true)
	public void updateTripInfo() {
		// call
		Locale locale = new Locale("ru");
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		String name = "Тестовая поездка No1";
		trip.getName().setValue(name, locale);
		trip.getSummary().setValue("Краткое описание тестовой поездки", locale);
		trip.getDescription().setValue("Полное описание тестовой поездки", locale);
		tripService.updateTripInfo(trip, locale);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getName());
		Assert.assertNotNull(trip.getName().getValue(locale));
		Assert.assertEquals(trip.getName().getValue(locale), name);
	}

	@Test(priority = 7, enabled = true)
	public void removeTripLocale() {
		// call
		Locale locale = new Locale("ru");
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getLangs());
		Assert.assertEquals(trip.getLangs().length, 2);
		tripService.removeTripLocale(trip.getId(), locale);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getLangs());
		Assert.assertEquals(trip.getLangs().length, 1);
	}

	@Test(priority = 8, enabled = true)
	public void abortTableItem() {
		// call
		String cause = "cause interruption of travel";
		tripService.abortTableItem(TRIP_ID, TABLE_ID, cause);
		// check
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		TableItem item = trip.getTable()[0];
		Assert.assertNotNull(item.getStatus());
		Assert.assertNotNull(item.getStatus().getValue());
		Assert.assertEquals(item.getStatus().getValue(), TableStatusEnum.ABORTED);
		Assert.assertNotNull(item.getStatus().getText());
		Assert.assertEquals(item.getStatus().getText(), cause);
	}

	@Test(priority = 9, enabled = true)
	public void cancelTableItem() {
		// call
		String cause = "cause interruption of travel";
		tripService.cancelTableItem(TRIP_ID, TABLE_ID, cause);
		// check
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		TableItem item = trip.getTable()[0];
		Assert.assertNotNull(item.getStatus());
		Assert.assertNotNull(item.getStatus().getValue());
		Assert.assertEquals(item.getStatus().getValue(), TableStatusEnum.CANCELED);
		Assert.assertNotNull(item.getStatus().getText());
		Assert.assertEquals(item.getStatus().getText(), cause);
	}

	@Test(priority = 10, enabled = false)
	public void addPost() {
		tripService.addPost(TRIP_ID, POST_ID);
	}

	@Test(priority = 11, enabled = false)
	public void getPosts() {
		// call
		List<Post> posts = tripService.getPosts(TRIP_ID);
		// check
		Assert.assertNotNull(posts);
		Assert.assertEquals(posts.size(), 1);
		Assert.assertEquals(posts.get(0), post);
	}

	@Test(priority = 12, enabled = false)
	public void removePost() {
		// call
		tripService.removePost(TRIP_ID, POST_ID);
		// check
		List<Post> posts = tripService.getPosts(TRIP_ID);
		Assert.assertTrue(posts == null || posts.size() == 0);
	}
}
