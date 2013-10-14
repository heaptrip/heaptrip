package com.heaptrip.service.content.trip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.util.RandomUtils;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitTripTest extends AbstractTestNGSpringContextTests {

	private static final String IMAGE_NAME = "penguins.jpg";

	private static final String REGION_NAME = "Russia Ukraine Belarus";

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
	private RegionService regionService;

	private SimpleCategory[] getCategories() {
		return new SimpleCategory[] { new SimpleCategory(TripDataProvider.CATEGORY_IDS[0]),
				new SimpleCategory(TripDataProvider.CATEGORY_IDS[1]) };
	}

	private SimpleRegion[] getRegions() throws SolrServerException {
		SimpleRegion[] simpleRegions = null;
		List<Region> regions = regionService.getRegionsByName(REGION_NAME, 0L, 10L, LanguageUtils.getEnglishLocale());
		if (regions != null) {
			TripDataProvider.REGION_IDS = new String[regions.size()];
			simpleRegions = new SimpleRegion[regions.size()];
			for (int i = 0; i < regions.size(); i++) {
				Region region = regions.get(i);
				TripDataProvider.REGION_IDS[i] = region.getId();
				SimpleRegion simpleRegion = new SimpleRegion();
				simpleRegion.setId(region.getId());
				simpleRegion.setName(region.getName());
				simpleRegions[i] = simpleRegion;
			}
		}
		return simpleRegions;
	}

	static TableItem[] getRandomTable() {
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
		SimpleCategory[] categories = getCategories();
		SimpleRegion[] regions = getRegions();
		List<Trip> trips = new ArrayList<>();
		for (String id : TripDataProvider.CONTENT_IDS) {
			Trip trip = new Trip();
			trip.setId(id);
			trip.setOwner(new ContentOwner(TripDataProvider.OWNER_ID));
			trip.setCategories(categories);
			trip.setRegions(regions);
			trip.setName(new MultiLangText("my name No " + id, LanguageUtils.getEnglishLocale()));
			trip.setSummary(new MultiLangText("my summary", LanguageUtils.getEnglishLocale()));
			trip.setDescription(new MultiLangText("my description", LanguageUtils.getEnglishLocale()));
			trip.setTable(getRandomTable());
			trip.setAllowed(new String[] { TripDataProvider.USER_ID });
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
		return tripService.saveTitleImage(IMAGE_NAME, is);
	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		trips = getTrips();
		image = getImage();
		for (Trip trip : trips) {
			trip.setImage(image);
			tripService.save(trip, LanguageUtils.getEnglishLocale());
			tripUserService.removeTripMembers(trip.getId());
		}
	}

	@AfterTest
	public void afterTest() {
		for (Trip trip : trips) {
			tripService.hardRemove(trip.getId());
			tripUserService.removeTripMembers(trip.getId());
		}
		if (image != null) {
			imageService.removeImage(image.getId());
		}
	}
}
