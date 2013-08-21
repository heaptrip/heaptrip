package com.heaptrip.service.content;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.criteria.ContextSearchCriteria;
import com.heaptrip.domain.service.content.criteria.ContentSearchResponse;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.util.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentSearchServiceTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = ContentSearchServiceTest.class.getName();

	private static String[] CATEGORY_IDS = InitContentTest.CATEGORY_IDS;

	private static String OWNER_ID = InitContentTest.OWNER_ID;

	private static String USER_ID = InitContentTest.USER_ID;

	static String[] REGION_IDS = null;

	static String REGION_NAME = "Izhevsk";

	@Autowired
	private ContentSearchService contentSearchService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private SolrContentRepository solrContentRepository;

	@Autowired
	private ContentRepository contentRepository;

	private Content trip = null;

	private SimpleCategory[] getCategories() {
		return new SimpleCategory[] { new SimpleCategory(CATEGORY_IDS[0]), new SimpleCategory(CATEGORY_IDS[1]) };
	}

	private SimpleRegion[] getRegions() throws SolrServerException {
		SimpleRegion[] simpleRegions = null;
		List<Region> regions = regionService.getRegionsByName(REGION_NAME, 0L, 10L, LanguageUtils.getEnglishLocale());
		if (regions != null) {
			REGION_IDS = new String[regions.size()];
			simpleRegions = new SimpleRegion[regions.size()];
			for (int i = 0; i < regions.size(); i++) {
				Region region = regions.get(i);
				REGION_IDS[i] = region.getId();
				SimpleRegion simpleRegion = new SimpleRegion();
				simpleRegion.setId(region.getId());
				simpleRegion.setName(region.getName());
				simpleRegions[i] = simpleRegion;
			}
		}
		return simpleRegions;
	}

	@BeforeClass
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
		trip = new Trip();
		trip.setId(TRIP_ID);
		trip.setOwner(new ContentOwner(OWNER_ID));
		trip.setName(new MultiLangText("test name", LanguageUtils.getEnglishLocale()));
		trip.setSummary(new MultiLangText("test summary", LanguageUtils.getEnglishLocale()));
		trip.setDescription(new MultiLangText("test description", LanguageUtils.getEnglishLocale()));
		trip.setCategories(getCategories());
		trip.setAllCategories(CATEGORY_IDS);
		trip.setRegions(getRegions());
		trip.setAllRegions(REGION_IDS);
		trip.setAllowed(new String[] { USER_ID });
		trip.setLangs(new String[] { LanguageUtils.getEnglishLocale().getLanguage() });
		// save to db
		contentRepository.save(trip);
		// save to solr
		solrContentRepository.save(trip);
	}

	@AfterClass
	public void relese() throws SolrServerException, IOException {
		if (trip != null && trip.getId() != null) {
			// remove from db
			contentRepository.remove(trip);
			// remove from solr
			solrContentRepository.remove(trip.getId());
		}
	}

	@Test(priority = 1, enabled = true, dataProvider = "contextSearchCriteria", dataProviderClass = ContentDataProvider.class)
	public void findContentsByСontextSearchCriteria(ContextSearchCriteria criteria) {
		// call
		ContentSearchResponse response = contentSearchService.findContentsByСontextSearchCriteria(criteria);
		// check
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getNumFound() > 0);
		Assert.assertNotNull(response.getContents());
		Assert.assertTrue(response.getContents().size() > 0);
		for (Content content : response.getContents()) {
			Assert.assertNotNull(content.getName());
			Assert.assertNotNull(content.getName().getValue(criteria.getLocale()));
			Assert.assertNotNull(content.getSummary());
			Assert.assertNotNull(content.getSummary().getValue(criteria.getLocale()));
		}
	}

}
