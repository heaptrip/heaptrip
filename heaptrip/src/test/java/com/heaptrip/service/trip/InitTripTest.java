package com.heaptrip.service.trip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentRegion;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.TripUserService;
import com.heaptrip.util.RandomUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitTripTest extends AbstractTestNGSpringContextTests {

	private static final String IMAGE_NAME = "penguins.jpg";

	static long TRIPS_COUNT = 20;

	static String OWNER_ID = "1";

	static String ALL_USERS = "0";

	static String USER_ID = "2";

	static String[] CATEGORY_IDS = new String[] { "1.2", "1.3" };

	static String REGION_NAME = "Russia Ukraine Belarus";

	private Locale locale = Locale.ENGLISH;

	private List<Trip> trips = null;

	private Image image = null;

	@Autowired
	private TripService tripService;

	@Autowired
	private TripUserService tripUserService;

	@Autowired
	private ResourceLoader loader;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ContentService contentService;

	@Autowired
	private RegionService regionService;

	private ContentCategory[] getCategories() {
		return new ContentCategory[] { new ContentCategory(CATEGORY_IDS[0]), new ContentCategory(CATEGORY_IDS[1]) };
	}

	private ContentRegion[] getRegions() throws SolrServerException {
		ContentRegion[] contentRegions = null;
		List<Region> regions = regionService.getRegionsByName(REGION_NAME, 0L, 10L, locale);
		if (regions != null) {
			contentRegions = new ContentRegion[regions.size()];
			for (int i = 0; i < regions.size(); i++) {
				Region region = regions.get(i);
				ContentRegion contentRegion = new ContentRegion();
				contentRegion.setId(region.getId());
				contentRegion.setName(region.getName());
				contentRegions[i] = contentRegion;
			}
		}
		return contentRegions;
	}

	private TableItem[] getRandomTable() {
		int tableSize = RandomUtils.getRandomInt(1, 10);
		TableItem[] table = new TableItem[tableSize];
		for (int j = 0; j < tableSize; j++) {
			TableItem item = new TableItem();
			item.setId(Integer.toString(j));
			Calendar startBegin = Calendar.getInstance();
			startBegin.set(2013, 0, 1);
			Calendar startEnd = Calendar.getInstance();
			startEnd.set(2013, 8, 1);
			Calendar dateEnd = Calendar.getInstance();
			dateEnd.set(2013, 11, 1);
			Date begin = RandomUtils.getRandomDate(startBegin.getTime(), startEnd.getTime());
			Date end = RandomUtils.getRandomDate(begin, dateEnd.getTime());
			item.setBegin(begin);
			item.setEnd(end);
			table[j] = item;
		}
		return table;
	}

	private List<Trip> getTrips() throws SolrServerException {
		ContentCategory[] categories = getCategories();
		ContentRegion[] regions = getRegions();
		List<Trip> trips = new ArrayList<>();
		for (int i = 0; i < TRIPS_COUNT; i++) {
			Trip trip = new Trip();
			trip.setId(Integer.toString(i));
			trip.setOwner(new ContentOwner(OWNER_ID));
			trip.setCategories(categories);
			trip.setRegions(regions);
			trip.setName(new MultiLangText("my name No " + Integer.toString(i), locale));
			trip.setSummary(new MultiLangText("my summary", locale));
			trip.setDescription(new MultiLangText("my description", locale));
			trip.setTable(getRandomTable());
			if (i % 2 == 0) {
				trip.setAllowed(new String[] { USER_ID });
			} else {
				trip.setAllowed(new String[] { ALL_USERS });
			}
			trips.add(trip);
		}
		return trips;
	}

	public Image getImage() throws IOException {
		Resource resource = loader.getResource(IMAGE_NAME);
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		return contentService.saveTitleImage(IMAGE_NAME, is);
	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		trips = getTrips();
		image = getImage();
		for (Trip trip : trips) {
			trip.setImage(image);
			tripService.saveTrip(trip, locale);
			tripUserService.removeTripMembers(trip.getId());
		}
	}

	@AfterTest
	public void afterTest() {
		for (Trip trip : trips) {
			// tripService.hardRemoveTrip(trip.getId());
			tripUserService.removeTripMembers(trip.getId());
		}
		if (image != null) {
			// imageStorageService.removeImage(image.getId());
		}
	}
}
