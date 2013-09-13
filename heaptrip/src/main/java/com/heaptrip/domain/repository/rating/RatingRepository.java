package com.heaptrip.domain.repository.rating;

import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating> {

	public long getCountByTargetId(String targetId);

	public void removeByTargetId(String targetId);
}
