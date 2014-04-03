package com.heaptrip.domain.repository.content.trip;

import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;

import java.util.List;

public interface TripMemberRepository extends CrudRepository<TripMember> {

    public List<TripMember> findByCriteria(TripMemberCriteria tripMemberCriteria);

    public long countByCriteria(TripMemberCriteria tripMemberCriteria);

    public List<String> findTripIdsByUserId(String userId);

    public boolean existsByTripIdAndUserId(String tripId, String userId);

    public void removeByTripId(String tripId);

    public void setStatus(String memberId, TableUserStatusEnum status);

    public void setOrganizer(String tableUserId, Boolean isOrganizer);
}
