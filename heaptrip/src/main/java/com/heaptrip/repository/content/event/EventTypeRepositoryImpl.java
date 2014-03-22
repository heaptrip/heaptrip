package com.heaptrip.repository.content.event;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.EventType;
import com.heaptrip.domain.repository.content.event.EventTypeRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.heaptrip.util.language.LanguageUtils;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class EventTypeRepositoryImpl extends CrudRepositoryImpl<EventType> implements EventTypeRepository {

    @Override
    protected Class<EventType> getCollectionClass() {
        return EventType.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.EVENT_TYPES.getName();
    }

    @Override
    public EventType findById(String id, Locale locale) {
        MongoCollection coll = getCollection();
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String fields = String.format("{'name.%s': 1}", lang);
        return coll.findOne("{ _id: #}", id).projection(fields).as(getCollectionClass());
    }

    @Override
    public List<EventType> findAll(Locale locale) {
        MongoCollection coll = getCollection();
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String fields = String.format("{'name.%s': 1}", lang);
        Iterable<EventType> iter = coll.find().projection(fields).sort("{_id: 1}").as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
    }

}
