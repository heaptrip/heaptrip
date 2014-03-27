package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Map;
import com.heaptrip.domain.entity.content.Marker;
import com.heaptrip.domain.entity.content.Point;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.exception.event.EventException;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.util.language.LanguageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventServiceTest extends AbstractTestNGSpringContextTests {

    private static final String EVENT_ID = "EVENT_FOR_EVENT_SERVICE_TEST";

    private static final String OWNER_ID = "OWNER_FOR_EVENT_SERVICE_TEST";

    private static final Locale LOCALE = LanguageUtils.getEnglishLocale();

    private static String[] CATEGORY_IDS = new String[]{"1.2", "1.3"};

    private static String[] EVENT_TYPE_IDS = new String[]{"1", "2", "3"};

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    private Event event = null;

    @BeforeClass
    public void init() {
        event = new Event();
        event.setId(EVENT_ID);
        event.setName(new MultiLangText("Test event"));
        event.setSummary(new MultiLangText("Summary for test event"));
        event.setDescription(new MultiLangText("Description for test event"));
        event.setOwnerId(OWNER_ID);
        // set categories
        SimpleCategory[] categories = new SimpleCategory[CATEGORY_IDS.length];
        for (int i = 0; i < CATEGORY_IDS.length; i++) {
            categories[i] = new SimpleCategory(CATEGORY_IDS[i]);
        }
        event.setCategories(categories);
        // set types
        EventType[] types = new EventType[EVENT_TYPE_IDS.length];
        for (int i = 0; i < EVENT_TYPE_IDS.length; i++) {
            types[i] = new EventType(EVENT_TYPE_IDS[i]);
        }
        event.setTypes(types);
    }

    @AfterClass(alwaysRun = true)
    public void release() {
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
        Assert.assertNotNull(event.getCategories());
        Assert.assertEquals(event.getCategories().length, CATEGORY_IDS.length);
        Assert.assertNotNull(event.getTypes());
        Assert.assertEquals(event.getTypes().length, EVENT_TYPE_IDS.length);
        Assert.assertNotNull(event.getCreated());
        Assert.assertNull(event.getDeleted());
        Assert.assertTrue(ArrayUtils.isEmpty(event.getAllowed()));
        Assert.assertNotNull(event.getMainLang());
        Assert.assertEquals(event.getMainLang(), LOCALE.getLanguage());
        Assert.assertTrue(ArrayUtils.isNotEmpty(event.getLangs()));
        Assert.assertEquals(event.getLangs().length, 1);
        Assert.assertEquals(event.getLangs()[0], LOCALE.getLanguage());
        Assert.assertEquals(event.getOwnerId(), OWNER_ID);
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
        Point[] points = new Point[]{new Point("1.1", "1.2"), new Point("2.1", "2.2"), new Point("3.1", "3.2")};
        Marker[] markers = new Marker[]{new Marker("1.1", "1.2", "Ночлег"), new Marker("3.1", "3.2", "Последний рубеж")};
        Map map = new Map(points, markers);
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
        Assert.assertNotNull(event.getCategories());
        Assert.assertEquals(event.getCategories().length, CATEGORY_IDS.length);
        Assert.assertNotNull(event.getTypes());
        Assert.assertEquals(event.getTypes().length, EVENT_TYPE_IDS.length);
        // check map
        Assert.assertNotNull(event.getMap());
        Assert.assertNotNull(event.getMap().getPoints());
        Assert.assertEquals(event.getMap().getPoints().length, points.length);
        for (int i = 0; i < event.getMap().getPoints().length; i++) {
            Point point = event.getMap().getPoints()[i];
            Assert.assertEquals(point.getOb(), points[i].getOb());
            Assert.assertEquals(point.getPb(), points[i].getPb());
        }
        Assert.assertNotNull(event.getMap().getMarkers());
        Assert.assertEquals(event.getMap().getMarkers().length, markers.length);
        for (int i = 0; i < event.getMap().getMarkers().length; i++) {
            Marker marker = event.getMap().getMarkers()[i];
            Assert.assertEquals(marker.getOb(), markers[i].getOb());
            Assert.assertEquals(marker.getPb(), markers[i].getPb());
            Assert.assertEquals(marker.getText(), markers[i].getText());
        }
        Assert.assertEquals(event.isShowMap(), true);
    }

    @Test(priority = 2, enabled = true)
    public void removeLocale() {
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

    @Test(priority = 3, enabled = true, expectedExceptions = EventException.class)
    public void removeMainLocale() {
        eventService.removeLocale(event.getId(), LOCALE);
    }

    @Test(priority = 4, enabled = true)
    public void remove() {
        // call
        eventService.remove(EVENT_ID);
        // check
        Event event = eventRepository.findOne(EVENT_ID);
        Assert.assertNotNull(event);
        Assert.assertNotNull(event.getDeleted());
    }

}
