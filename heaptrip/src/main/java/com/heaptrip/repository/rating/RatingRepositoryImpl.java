package com.heaptrip.repository.rating;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.domain.repository.rating.RatingSum;
import com.heaptrip.repository.CrudRepositoryImpl;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Iterator;

@Service
public class RatingRepositoryImpl extends CrudRepositoryImpl<Rating> implements RatingRepository {

    @Override
    protected Class<Rating> getCollectionClass() {
        return Rating.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.RATINGS.getName();
    }

    @Override
    public RatingSum getRatingSumByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        return coll.aggregate("{$match : {targetId: #}}", targetId)
                .and(" {$group: {_id: '', count: {$sum: 1 }, sum: {$sum: '$value'}}} ").as(RatingSum.class).get(0);
    }

    @Override
    public RatingSum getRatingSumByTargetIdAndCreatedLessThenHalfYear(String targetId) {
        MongoCollection coll = getCollection();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        return coll.aggregate("{$match : {targetId: #, created: {$gte: #}}}", targetId, calendar.getTime())
                .and(" {$group: {_id: '', count: {$sum: 1 }, sum: {$sum: '$value'}}} ").as(RatingSum.class).get(0);
    }

    @Override
    public long getCountByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        return coll.count("{targetId: #}", targetId);
    }

    @Override
    public void removeByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        coll.remove("{targetId: #}", targetId);
    }

    @Override
    public Rating findByTargetIdAndUserId(String targetId, String userId) {
        MongoCollection coll = getCollection();
        return coll.findOne("{targetId: #, userId: #}", targetId, userId).as(getCollectionClass());
    }

    @Override
    public Rating findOldestByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        Iterator<Rating> iter = coll.find("{targetId: #}", targetId).sort("{created: 1}")
                .hint("{targetId: 1, created: 1}").limit(1).as(getCollectionClass()).iterator();
        return (iter.hasNext()) ? iter.next() : null;
    }
}
