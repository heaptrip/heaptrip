package com.heaptrip.domain.repository.content.event;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.repository.CrudRepository;

public interface EventTypeRepository extends CrudRepository<EventType> {

	public EventType findById(String id, Locale locale);

	public List<EventType> findAll(Locale locale);

}
