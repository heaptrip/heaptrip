package com.heaptrip.repository.content.trip;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TripMemberRepositoryImpl extends CrudRepositoryImpl<TripMember> implements TripMemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(TripMemberRepositoryImpl.class);

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MEMBERS.getName();
    }

    @Override
    protected Class<TripMember> getCollectionClass() {
        return TripMember.class;
    }

    @Override
    public List<TripMember> findByCriteria(TripMemberCriteria tripMemberCriteria) {
        QueryHelper<TripMemberCriteria, TripMember> queryHelper = queryHelperFactory.getHelperByCriteria(tripMemberCriteria);
        return queryHelper.findByCriteria(tripMemberCriteria);
    }

    @Override
    public long countByCriteria(TripMemberCriteria tripMemberCriteria) {
        QueryHelper<TripMemberCriteria, TripMember> queryHelper = queryHelperFactory.getHelperByCriteria(tripMemberCriteria);
        return queryHelper.countByCriteria(tripMemberCriteria);
    }

    @Override
    public List<String> findTripIdsByUserId(String userId) {
        MongoCollection coll = getCollection();
        String query = "{userId: #}";
        String projection = "{_class: 1, contentId: 1}";
        String hint = "{userId: 1, contentId: 1}";
        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "find trip members\n->query: %s\n->parameters: %s\n->projection: %s\n->hint: %s", query, userId,
                    projection, hint);
            logger.debug(msg);
        }
        Iterator<TripMember> iter = coll.find(query, userId).projection(projection).hint(hint).as(getCollectionClass())
                .iterator();
        List<String> result = new ArrayList<>();
        if (iter != null) {
            while (iter.hasNext()) {
                TripMember member = iter.next();
                result.add(member.getContentId());
            }
        }
        return result;
    }

    @Override
    public boolean existsByTripIdAndUserIdAndStatusOk(String tripId, String userId) {
        MongoCollection coll = getCollection();
        long count = coll.count("{userId: #, contentId: #, status: #}", userId, tripId, TableUserStatusEnum.OK);
        return count > 0;
    }

    @Override
    public boolean existsByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId) {
        MongoCollection coll = getCollection();
        long count = coll.count("{userId: #, contentId: #, tableId: #}", userId, tripId, tableId);
        return count > 0;
    }

    @Override
    public boolean existsByTripIdAndTableIdAndEmail(String tripId, String tableId, String email) {
        MongoCollection coll = getCollection();
        long count = coll.count("{contentId: #, tableId: #, email: #}", tripId, tableId, email);
        return count > 0;
    }

    @Override
    public void setStatus(String memberId, TableUserStatusEnum status) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", memberId).with("{$set: {status: #}}", status);
        logger.debug("WriteResult for set status: {}", wr);
    }

    @Override
    public void setStatusByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId, TableUserStatusEnum status) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{userId: #, contentId: #, tableId: #}", userId, tripId, tableId).with("{$set: {status: #}}", status);
        logger.debug("WriteResult for set status: {}", wr);
    }

    @Override
    public void setOrganizer(String tableUserId, Boolean isOrganizer) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", tableUserId).with("{$set: {isOrganizer: #}}", isOrganizer);
        logger.debug("WriteResult for set organizer: {}", wr);
    }

    @Override
    public void removeByTripId(String tripId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{contentId: #}", tripId);
        logger.debug("WriteResult for remove trip member by tripId: {}", wr);
    }

    @Override
    public void removeByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{userId: #, contentId: #, tableId: #}", userId, tripId, tableId);
        logger.debug("WriteResult for remove trip member by tripId and  tableId and userId: {}", wr);
    }
}
