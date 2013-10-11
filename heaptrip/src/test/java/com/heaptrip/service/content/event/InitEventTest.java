package com.heaptrip.service.content.event;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.domain.service.content.event.EventTypeService;
import com.heaptrip.domain.service.content.event.EventUserService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.service.content.ContentDataProvider;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitEventTest extends AbstractTestNGSpringContextTests {

	private static String REGION_NAME = "Izhevsk";

	@Autowired
	private EventService eventService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private EventTypeService eventTypeService;

	@Autowired
	private EventUserService eventUserService;

	private SimpleCategory[] getCategories() {
		return new SimpleCategory[] { new SimpleCategory(ContentDataProvider.CATEGORY_IDS[0]),
				new SimpleCategory(ContentDataProvider.CATEGORY_IDS[1]) };
	}

	private SimpleRegion[] getRegions() throws SolrServerException {
		SimpleRegion[] simpleRegions = null;
		List<Region> regions = regionService.getRegionsByName(REGION_NAME, 0L, 10L, LanguageUtils.getEnglishLocale());
		if (regions != null) {
			ContentDataProvider.REGION_IDS = new String[regions.size()];
			simpleRegions = new SimpleRegion[regions.size()];
			for (int i = 0; i < regions.size(); i++) {
				Region region = regions.get(i);
				ContentDataProvider.REGION_IDS[i] = region.getId();
				SimpleRegion simpleRegion = new SimpleRegion();
				simpleRegion.setId(region.getId());
				simpleRegion.setName(region.getName());
				simpleRegions[i] = simpleRegion;
			}
		}
		return simpleRegions;
	}

	private EventType[] getEventTypes() {
		EventType[] eventTypes = new EventType[EventDataProvider.EVENT_TYPE_IDS.length];
		for (int i = 0; i < EventDataProvider.EVENT_TYPE_IDS.length; i++) {
			String eventTypeId = EventDataProvider.EVENT_TYPE_IDS[i];
			EventType type = eventTypeService.getEventType(eventTypeId, LanguageUtils.getEnglishLocale());
			Assert.assertNotNull(type);
			eventTypes[i] = type;
		}
		return eventTypes;
	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		Event event = new Event();
		event.setId(EventDataProvider.CONTENT_ID);
		ContentOwner owner = new ContentOwner();
		owner.setId(EventDataProvider.OWNER_ID);
		event.setOwner(owner);
		event.setCategories(getCategories());
		event.setRegions(getRegions());
		event.setTypes(getEventTypes());
		event.setName(new MultiLangText("Test event"));
		event.setSummary(new MultiLangText("Summary for test event"));
		event.setDescription(new MultiLangText("Description for test event"));
		eventService.save(event, LanguageUtils.getEnglishLocale());
		eventUserService.removeEventMembers(event.getId());
	}

	@AfterTest
	public void afterTest() {
		eventService.hardRemove(EventDataProvider.CONTENT_ID);
		eventUserService.removeEventMembers(EventDataProvider.CONTENT_ID);

	}
}
