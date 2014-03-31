package com.heaptrip.repository.content.event;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.content.event.EventMemberRepository;
import com.heaptrip.domain.service.content.event.criteria.EventMemberCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventMemberRepositoryImpl extends CrudRepositoryImpl<EventMember> implements EventMemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(EventMemberRepositoryImpl.class);

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected Class<EventMember> getCollectionClass() {
        return EventMember.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MEMBERS.getName();
    }

    @Override
    public List<EventMember> findByCriteria(EventMemberCriteria memberCriteria) {
        QueryHelper<EventMemberCriteria, EventMember> queryHelper = queryHelperFactory.getHelperByCriteria(memberCriteria);
        return queryHelper.findByCriteria(memberCriteria);
    }

    @Override
    public long countByCriteria(EventMemberCriteria memberCriteria) {
        QueryHelper<EventMemberCriteria, EventMember> queryHelper = queryHelperFactory.getHelperByCriteria(memberCriteria);
        return queryHelper.countByCriteria(memberCriteria);
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
