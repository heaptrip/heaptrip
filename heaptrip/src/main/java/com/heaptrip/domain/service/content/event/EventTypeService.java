package com.heaptrip.domain.service.content.event;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.event.EventType;

/**
 * 
 * Service to work with type of events
 * 
 */
public interface EventTypeService {

	/**
	 * Get event type by id
	 * 
	 * @param eventTypeId
	 *            event type id
	 * @param locale
	 *            locale
	 * @return event type
	 */
	public EventType getEventType(String eventTypeId, Locale locale);

	/**
	 * Get all event types
	 * 
	 * @param locale
	 *            locale
	 * @return list of event types
	 */
	public List<EventType> getAllEventTypes(Locale locale);
}
