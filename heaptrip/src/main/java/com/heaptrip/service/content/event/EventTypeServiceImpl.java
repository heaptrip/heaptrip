package com.heaptrip.service.content.event;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.repository.content.event.EventTypeRepository;
import com.heaptrip.domain.service.content.event.EventTypeService;

@Service
public class EventTypeServiceImpl implements EventTypeService {

	@Autowired
	private EventTypeRepository eventTypeRepository;

	@Override
	public EventType getEventType(String eventTypeId, Locale locale) {
		Assert.notNull(eventTypeId, "eventTypeId must not be null");
		Assert.notNull(locale, "locale must not be null");
		return eventTypeRepository.findById(eventTypeId, locale);
	}

	@Override
	public List<EventType> getAllEventTypes(Locale locale) {
		Assert.notNull(locale, "locale must not be null");
		return eventTypeRepository.findAll(locale);
	}

}
