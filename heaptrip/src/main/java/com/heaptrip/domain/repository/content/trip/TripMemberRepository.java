package com.heaptrip.domain.repository.content.trip;

import java.util.List;

import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.repository.CrudRepository;

public interface TripMemberRepository extends CrudRepository<TripMember> {

	public List<TripMember> findByTripIdAndTableId(String tripId, String tableId);

	public List<TripMember> findByTripIdAndTableId(String tripId, String tableId, int limit);

	public List<TripUser> findByUserId(String tripId, String userId);

	public boolean existsByTripIdAndUserId(String tripId, String userId);

	public long getCountByTripId(String tripId);

	public List<String> findTripIdsByUserId(String userId);

	public void removeByTripId(String tripId);

	public void setStatus(String memberId, TableUserStatusEnum status);

	public void setOrganizer(String tableUserId, Boolean isOrganizer);
}
