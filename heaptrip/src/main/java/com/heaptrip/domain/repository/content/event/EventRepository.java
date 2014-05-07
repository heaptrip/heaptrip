package com.heaptrip.domain.repository.content.event;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;

import java.util.List;
import java.util.Locale;

public interface EventRepository extends CrudRepository<Event> {

    public List<Event> findByFeedCriteria(EventFeedCriteria criteria);

    public List<Event> findByMyAccountCriteria(EventMyAccountCriteria criteria);

    public List<Event> findByForeignAccountCriteria(EventForeignAccountCriteria criteria);

    public long getCountByFeedCriteria(EventFeedCriteria criteria);

    public long getCountByMyAccountCriteria(EventMyAccountCriteria criteria);

    public long getCountByForeignAccountCriteria(EventForeignAccountCriteria criteria);

    public Event findByIdAndLocale(String contentId, Locale locale);

    public void update(Event event, Locale locale);

    public void removeLanguage(String contentId, Locale locale);

    public void incMembers(String contentId, int value);

    public void resetMembers(String contentId);

    public Event getMainLanguage(String contentId);
}
