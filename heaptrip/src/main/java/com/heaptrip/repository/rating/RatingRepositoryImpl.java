package com.heaptrip.repository.rating;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

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

}
