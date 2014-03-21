package com.heaptrip.repository.content.event;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.content.event.EventMemberRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.content.trip.TripMemberRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class EventMemberRepositoryImpl extends CrudRepositoryImpl<EventMember> implements EventMemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(TripMemberRepositoryImpl.class);

    @Override
    protected Class<EventMember> getCollectionClass() {
        return EventMember.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MEMBERS.getName();
    }

    @Override
    public List<EventMember> findByContentId(String contentId) {
        MongoCollection coll = getCollection();
        String query = "{contentId: #}";
        Iterator<EventMember> iter = coll.find(query, contentId).as(getCollectionClass()).iterator();
        return IteratorConverter.copyIterator(iter);
    }

    @Override
    public List<EventMember> findByContentId(String contentId, int limit) {
        MongoCollection coll = getCollection();
        String query = "{contentId: #}";
        Iterator<EventMember> iter = coll.find(query, contentId).limit(limit).as(getCollectionClass()).iterator();
        return IteratorConverter.copyIterator(iter);
    }

    @Override
    public boolean existsByContentIdAndUserId(String contentId, String userId) {
        MongoCollection coll = getCollection();
        long count = coll.count("{userId: #, contentId: #}", userId, contentId);
        return count > 0;
    }

    @Override
    public void removeByContentIdAndUserId(String contentId, String userId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{userId: #, contentId: #}", userId, contentId);
        logger.debug("WriteResult for remove event member by contentId and userId: {}", wr);
    }

    @Override
    public void removeByContentId(String contentId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{contentId: #}", contentId);
        logger.debug("WriteResult for remove event members by contentId: {}", wr);
    }

}
