package com.heaptrip.domain.service.content.event;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.service.content.ContentService;

import java.util.Locale;

/**
 * Event service
 */
public interface EventService extends ContentService {

    /**
     * Save a new event
     *
     * @param event  event
     * @param locale the locale for which to create the event
     * @return content
     */
    public Event save(Event event, Locale locale);

    /**
     * Get base information of the event
     *
     * @param eventId event id
     * @param locale  locale
     * @return event event
     */
    public Event get(String eventId, Locale locale);

    /**
     * Update base information of the event
     *
     * @param event  event
     * @param locale the locale for which to update the event
     */
    public void update(Event event, Locale locale);

    /**
     * Remove event locale
     *
     * @param eventId event
     * @param locale  locale
     */
    public void removeLocale(String eventId, Locale locale);

}
