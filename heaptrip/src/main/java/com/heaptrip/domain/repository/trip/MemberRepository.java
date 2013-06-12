package com.heaptrip.domain.repository.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;

public interface MemberRepository {

	public void save(TableMember member);

	public TableMember findById(String memberId);

	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId);

	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId, int limit);

	public long getCountByTripId(String tripId);

	public List<String> findTripIdsByUserId(String userId);

	public void removeById(String memberId);

	public void removeByTripId(String tripId);

	public void setStatus(String memberId, TableUserStatusEnum status);

	public void setOrganizer(String tableUserId, Boolean isOrganizer);

	public void addAllowed(String ownerId, String userId);

	public void removeAllowed(String ownerId, String userId);
}
