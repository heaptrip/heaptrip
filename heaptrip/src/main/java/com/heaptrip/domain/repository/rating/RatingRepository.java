package com.heaptrip.domain.repository.rating;

import java.util.List;

import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating> {

	public List<Rating> findByTargetId(String targetId);

	public List<Rating> findByTargetIdAndCreatedLessThenHalfYear(String targetId);

	public Rating findByTargetIdAndUserId(String targetId, String userId);

	public long getCountByTargetId(String targetId);

	public void removeByTargetId(String targetId);

	public Rating findOldestByTargetId(String targetId);

}
