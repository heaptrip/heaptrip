package com.heaptrip.repository.rating;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;

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
	public List<Rating> findByTargetId(String targetId) {
		MongoCollection coll = getCollection();
		Iterator<Rating> iter = coll.find("{targetId: #}", targetId).as(getCollectionClass()).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public List<Rating> findByTargetIdAndCreatedLessThenHalfYear(String targetId) {
		MongoCollection coll = getCollection();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		Iterator<Rating> iter = coll.find("{targetId: #, created: {$gte: #}}", targetId, calendar.getTime())
				.hint("{targetId: 1, created: 1}").as(getCollectionClass()).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public Rating findOldestByTargetId(String targetId) {
		MongoCollection coll = getCollection();
		Iterator<Rating> iter = coll.find("{targetId: #}", targetId).sort("{created: 1}")
				.hint("{targetId: 1, created: 1}").limit(1).as(getCollectionClass()).iterator();
		return (iter.hasNext()) ? iter.next() : null;
	}

}
