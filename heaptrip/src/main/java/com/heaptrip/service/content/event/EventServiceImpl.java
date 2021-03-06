package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Views;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.event.EventException;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.repository.content.event.EventTypeRepository;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.service.content.ContentServiceImpl;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;

@Service
public class EventServiceImpl extends ContentServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Override
    public Event save(Event event, Locale locale) {
        Assert.notNull(event, "event must not be null");
        Assert.notNull(locale, "locale must not be null");
        Assert.notNull(event.getOwnerId(), "owner.id must not be null");
        Assert.notEmpty(event.getName(), "name must not be empty");
        Assert.notEmpty(event.getSummary(), "summary must not be empty");
        Assert.notEmpty(event.getDescription(), "description must not be empty");

        // update categories and categoryIds
        updateCategories(event);

        // update regions and regionIds
        updateRegions(event);

        // update event types
        updateTypes(event);

        // set lang
        String lang = LanguageUtils.getLanguageByLocale(locale);
        event.setLangs(new String[]{lang});
        event.setMainLang(lang);
        event.getName().setMainLanguage(lang);
        event.getSummary().setMainLanguage(lang);
        event.getDescription().setMainLanguage(lang);

        event.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
        event.setAllowed(new String[]{});

        event.setCreated(new Date());
        event.setDeleted(null);
        event.setRating(ContentRating.getDefaultValue());
        event.setComments(0L);
        event.setMembers(0);

        Views views = new Views();
        views.setCount(0);
        event.setViews(views);

        // save to db
        eventRepository.save(event);

        // save to solr
        contentSearchService.saveContent(event.getId());

        return event;
    }

    @Override
    public Event get(String eventId, Locale locale) {
        Assert.notNull(eventId, "eventId must not be null");
        Assert.notNull(locale, "locale must not be null");
        return eventRepository.findByIdAndLocale(eventId, locale);
    }

    @Override
    public void update(Event event, Locale locale) {
        Assert.notNull(event, "event must not be null");
        Assert.notNull(locale, "locale must not be null");
        Assert.notNull(event.getId(), "event.id must not be null");
        Assert.notNull(event.getMainLang(), "mainLang must not be empty");
        Assert.notEmpty(event.getName(), "name must not be empty");
        Assert.notEmpty(event.getSummary(), "summary must not be empty");
        Assert.notEmpty(event.getDescription(), "description must not be empty");
        Assert.notEmpty(event.getLangs(), "langs must not be empty");

        // update categories and categoryIds
        updateCategories(event);

        // update regions and regionIds
        updateRegions(event);

        // update event types
        updateTypes(event);

        // set lang
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String mainLang = eventRepository.getMainLanguage(event.getId()).getMainLang();
        event.setMainLang(mainLang);

        if (mainLang.equals(lang)) {
            event.getName().setMainLanguage(mainLang);
            event.getSummary().setMainLanguage(mainLang);
            event.getDescription().setMainLanguage(mainLang);
        }

        // update to db
        eventRepository.update(event, locale);

        // save to solr
        contentSearchService.saveContent(event.getId());
    }

    @Override
    public void removeLocale(String eventId, Locale locale) {
        Assert.notNull(eventId, "eventId must not be null");
        Assert.notNull(locale, "locale must not be null");

        String removeLang = LanguageUtils.getLanguageByLocale(locale);
        String mainLang = contentRepository.getMainLanguage(eventId);
        if (mainLang == null) {
            throw new IllegalArgumentException(String.format("event with id=%s is not found", eventId));
        }
        if (mainLang.equals(removeLang)) {
            throw errorService.createException(EventException.class, ErrorEnum.REMOVE_EVENT_LANGUAGE_FAILURE);
        }

        // remove frome db
        eventRepository.removeLanguage(eventId, locale);

        // save to solr
        contentSearchService.saveContent(eventId);
    }

    @Override
    public void remove(String contentId) {
        super.remove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    @Override
    public void hardRemove(String contentId) {
        super.hardRemove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    private void updateTypes(Event content) {
        if (content.getTypes() != null) {
            for (EventType type : content.getTypes()) {
                Assert.notNull(type.getId(), "type.id must not be null");
                EventType eventType = eventTypeRepository.findOne(type.getId());
                Assert.notNull(eventType, String.format("error type.id: %s", type.getId()));
                type.setName(eventType.getName());
            }
        }
    }

}
