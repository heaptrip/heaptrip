package com.heaptrip.repository.rating;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Service
public class RatingRepositoryImpl extends CrudRepositoryImpl<Rating> implements RatingRepository{

	@Override
	public long getCountByTargetId(String targetId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeByTargetId(String targetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Class<Rating> getCollectionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCollectionName() {
		// TODO Auto-generated method stub
		return null;
	}

}
