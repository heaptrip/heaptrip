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

    public boolean existsByTripIdAndUserIdAndStatusOk(String tripId, String userId);

    public boolean existsByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId);

    public boolean existsByTripIdAndTableIdAndEmail(String tripId, String tableId, String email);

    public void setStatus(String memberId, TableUserStatusEnum status);

    public void setStatusByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId, TableUserStatusEnum status);

    public void setOrganizer(String tableUserId, Boolean isOrganizer);

    public void removeByTripId(String tripId);

    public void removeByTripIdAndTableIdAndUserId(String tripId, String tableId, String userId);
}
