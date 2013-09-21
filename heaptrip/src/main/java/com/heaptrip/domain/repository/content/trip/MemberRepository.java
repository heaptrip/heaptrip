package com.heaptrip.domain.repository.content.trip;

import java.util.List;

import com.heaptrip.domain.entity.content.trip.TableMember;
import com.heaptrip.domain.entity.content.trip.TableUser;
import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<TableMember> {

	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId);

	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId, int limit);

	public List<TableUser> findTableUsersByUserId(String tripId, String userId);

	public boolean existsByTripIdAndUserId(String tripId, String userId);

	public long getCountByTripId(String tripId);

	public List<String> findTripIdsByUserId(String userId);

	public void removeByTripId(String tripId);

	public void setStatus(String memberId, TableUserStatusEnum status);

	public void setOrganizer(String tableUserId, Boolean isOrganizer);

	public void addAllowed(String ownerId, String userId);

	public void removeAllowed(String ownerId, String userId);
}
