package com.heaptrip.service.content;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentRegion;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitContentTest extends AbstractTestNGSpringContextTests {

	static String[] CATEGORY_IDS = new String[] { "2.4.7", "3.2" };

	static String REGION_NAME = "Izhevsk";

	static String TRIP_ID = "11";

	static String OWNER_ID = "1";

	static String USER_ID = "1";

	private Trip trip = null;

	@Autowired
	private TripService tripService;

	@Autowired
	private RegionService regionService;

	Locale locale = Locale.ENGLISH;

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

	@BeforeTest()
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
		trip = new Trip();
		trip.setId(TRIP_ID);
		trip.setOwner(new ContentOwner(OWNER_ID));
		trip.setName(new MultiLangText("test name", locale));
		trip.setSummary(new MultiLangText("test summary", locale));
		trip.setDescription(new MultiLangText("test description", locale));
		trip.setCategories(getCategories());
		trip.setRegions(getRegions());
		tripService.saveTrip(trip, locale);
	}

	@AfterTest
	public void afterTest() {
		if (trip != null) {
			tripService.hardRemoveTrip(trip.getId());
		}
	}
}
