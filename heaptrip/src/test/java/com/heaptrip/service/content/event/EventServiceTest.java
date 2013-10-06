package com.heaptrip.service.content.event;

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventServiceTest extends AbstractTestNGSpringContextTests {

	private static final String EVENT_ID = "EVENT_FOR_EVENT_SERVICE_TEST";

	private static final String OWNER_ID = "OWNER_FOR_EVENT_SERVICE_TEST";

	private static final Locale LOCALE = LanguageUtils.getEnglishLocale();

	@Autowired
	private EventService eventService;

	@Autowired
	private EventRepository eventRepository;

	private Event event = null;

	@BeforeClass
	public void init() {
		event = new Event();
		event.setId(EVENT_ID);
		ContentOwner owner = new ContentOwner();
		owner.setId(OWNER_ID);
		event.setName(new MultiLangText("Test event"));
		event.setSummary(new MultiLangText("Summary for test event"));
		event.setDescription(new MultiLangText("Description for test event"));
		event.setOwner(owner);
	}

	@AfterClass(alwaysRun = true)
	public void relese() {
		eventService.hardRemove(EVENT_ID);
	}

	@Test(priority = 0, enabled = true)
	public void save() {
		// call
		eventService.save(event, LOCALE);
		// check
		Event event = eventRepository.findOne(EVENT_ID);
		Assert.assertNotNull(event);
		Assert.assertEquals(event, this.event);
		Assert.assertNotNull(event.getCreated());
		Assert.assertNull(event.getDeleted());
		Assert.assertTrue(ArrayUtils.isEmpty(event.getAllowed()));
		Assert.assertNotNull(event.getMainLang());
		Assert.assertEquals(event.getMainLang(), LOCALE.getLanguage());
		Assert.assertTrue(ArrayUtils.isNotEmpty(event.getLangs()));
		Assert.assertEquals(event.getLangs().length, 1);
		Assert.assertEquals(event.getLangs()[0], LOCALE.getLanguage());
		Assert.assertNotNull(event.getOwner());
		Assert.assertNotNull(event.getOwner().getId());
		Assert.assertEquals(event.getOwner().getId(), OWNER_ID);
		Assert.assertNotNull(event.getStatus());
		Assert.assertNotNull(event.getStatus().getValue());
		Assert.assertEquals(event.getStatus().getValue(), ContentStatusEnum.DRAFT);
		Assert.assertNotNull(event.getRating());
		Assert.assertEquals(event.getRating().getCount(), 0);
		Assert.assertNotNull(event.getViews());
		Assert.assertEquals(event.getViews().getCount(), 0);
	}

	@Test(priority = 1, enabled = true)
	public void update() {
		// call
		Locale locale = new Locale("ru");
		Event event = eventRepository.findOne(EVENT_ID);
		Assert.assertNotNull(event);
		String name = "Тестовое событие No1";
		String summary = "Краткое описание тестового события";
		String description = "Полное описание тестового события";
		String map = "google map";
		event.getName().setValue(name, locale);
		event.getSummary().setValue(summary, locale);
		event.getDescription().setValue(description, locale);
		event.setMap(map);
		event.setShowMap(true);
		eventService.update(event, locale);
		// check
		event = eventService.get(EVENT_ID, locale);
		Assert.assertNotNull(event);
		Assert.assertNotNull(event.getName());
		Assert.assertNotNull(event.getName().getValue(locale));
		Assert.assertEquals(event.getName().getValue(locale), name);
		Assert.assertNotNull(event.getSummary());
		Assert.assertNotNull(event.getSummary().getValue(locale));
		Assert.assertEquals(event.getSummary().getValue(locale), summary);
		Assert.assertNotNull(event.getDescription());
		Assert.assertNotNull(event.getDescription().getValue(locale));
		Assert.assertEquals(event.getDescription().getValue(locale), description);
		Assert.assertNotNull(event.getMap());
		Assert.assertEquals(event.getMap(), map);
		Assert.assertEquals(event.isShowMap(), true);
	}

	@Test(priority = 2, enabled = true)
	public void removeTripLocale() {
		// call
		Locale locale = new Locale("ru");
		Event event = eventService.get(EVENT_ID, LOCALE);
		Assert.assertNotNull(event);
		Assert.assertNotNull(event.getLangs());
		Assert.assertEquals(event.getLangs().length, 2);
		eventService.removeLocale(event.getId(), locale);
		// check
		event = eventService.get(EVENT_ID, LOCALE);
		Assert.assertNotNull(event);
		Assert.assertNotNull(event.getLangs());
		Assert.assertEquals(event.getLangs().length, 1);
	}

}
