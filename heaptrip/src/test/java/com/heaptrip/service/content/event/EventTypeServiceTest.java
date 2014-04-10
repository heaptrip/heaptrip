package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.repository.content.event.EventTypeRepository;
import com.heaptrip.domain.service.content.event.EventTypeService;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventTypeServiceTest extends AbstractTestNGSpringContextTests {

    private List<EventType> eventTypes = new ArrayList<>();

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventTypeService eventTypeService;

    @BeforeClass
    public void init() {
        eventTypes.add(new EventType("1", "Семинар", "Trening"));
        eventTypes.add(new EventType("2", "Мастер класс", "Master class"));
        eventTypes.add(new EventType("3", "Вебинар", "Webinar"));
        eventTypes.add(new EventType("4", "Круглый стол", "Round table"));
        eventTypes.add(new EventType("5", "Соревнование", "Competition"));
    }

    @Test(priority = 0)
    public void removeAll() {
        eventTypeRepository.removeAll();
    }

    @Test(priority = 1)
    public void save() {
        eventTypeRepository.save(eventTypes);
    }

    @Test(priority = 2)
    public void findById() {
        String id = "1";
        EventType eventType = eventTypeService.getEventType(id, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(eventType);
        Assert.assertEquals(eventType.getId(), id);
    }

    @Test(priority = 3)
    public void findAll() {
        List<EventType> eventTypes = eventTypeService.getAllEventTypes(Locale.ENGLISH);
        Assert.assertEqualsNoOrder(eventTypes.toArray(), this.eventTypes.toArray());
    }
}
