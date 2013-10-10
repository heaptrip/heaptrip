package com.heaptrip.service.content.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.event.EventFeedService;
import com.heaptrip.domain.service.content.event.EventTypeService;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventFeedServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private EventTypeService eventTypeService;

	@Autowired
	private EventFeedService eventFeedService;

	@Autowired
	private ContentRepository contentRepository;

	private Event event = null;

	private EventType[] getEventTypes() {
		EventType[] eventTypes = new EventType[EventFeedDataProvider.EVENT_TYPE_IDS.length];
		for (int i = 0; i < EventFeedDataProvider.EVENT_TYPE_IDS.length; i++) {
			String eventTypeId = EventFeedDataProvider.EVENT_TYPE_IDS[i];
			EventType type = eventTypeService.getEventType(eventTypeId, LanguageUtils.getEnglishLocale());
			Assert.assertNotNull(type);
			eventTypes[i] = type;
		}
		return eventTypes;
	}

	@BeforeClass
	public void init() {
		event = new Event();
		event.setId(EventFeedDataProvider.CONTENT_ID);
		ContentOwner owner = new ContentOwner();
		owner.setId(EventFeedDataProvider.OWNER_ID);
		event.setOwner(owner);
		event.setAllowed(new String[] { EventFeedDataProvider.USER_ID });
		event.setCategoryIds(EventFeedDataProvider.CATEGORY_IDS);
		event.setRegionIds(EventFeedDataProvider.REGION_IDS);
		event.setTypes(getEventTypes());
		ContentStatus status = new ContentStatus();
		status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
		event.setStatus(status);
		contentRepository.save(event);
	}

	@AfterClass(alwaysRun = true)
	public void relese() {
		contentRepository.remove(EventFeedDataProvider.CONTENT_ID);
	}

	@Test(priority = 0, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "feedCriteria")
	public void getContentsByFeedCriteria(EventFeedCriteria feedCriteria) {
		// call
		List<Event> contents = eventFeedService.getContentsByFeedCriteria(feedCriteria);
		// check
		Assert.assertNotNull(contents);
		Assert.assertEquals(contents.size(), 1);
		Assert.assertEquals(contents.get(0), event);
	}

	@Test(priority = 1, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "myAccountCriteria")
	public void getContentsByMyAccountCriteria(EventMyAccountCriteria myAccountCriteria) {
		// call
		List<Event> contents = eventFeedService.getContentsByMyAccountCriteria(myAccountCriteria);
		// check
		Assert.assertNotNull(contents);
		Assert.assertEquals(contents.size(), 1);
		Assert.assertEquals(contents.get(0), event);
	}

	@Test(priority = 2, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
	public void getContentsByForeignAccountCriteria(EventForeignAccountCriteria foreignAccountCriteria) {
		// call
		List<Event> contents = eventFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
		// check
		Assert.assertNotNull(contents);
		Assert.assertEquals(contents.size(), 1);
		Assert.assertEquals(contents.get(0), event);
	}

	@Test(priority = 3, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "feedCriteria")
	public void getCountByFeedCriteria(EventFeedCriteria feedCriteria) {
		// call
		long count = eventFeedService.getCountByFeedCriteria(feedCriteria);
		// check
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 4, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "myAccountCriteria")
	public void getCountByMyAccountCriteria(EventMyAccountCriteria myAccountCriteria) {
		// call
		long count = eventFeedService.getCountByMyAccountCriteria(myAccountCriteria);
		// check
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 5, enabled = true, dataProviderClass = EventFeedDataProvider.class, dataProvider = "foreignAccountCriteria")
	public void getCountByForeignAccountCriteria(EventForeignAccountCriteria foreignAccountCriteria) {
		// call
		long count = eventFeedService.getCountByForeignAccountCriteria(foreignAccountCriteria);
		// check
		Assert.assertEquals(count, 1);
	}

}
