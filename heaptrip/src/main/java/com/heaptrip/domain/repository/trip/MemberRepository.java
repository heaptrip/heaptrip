package com.heaptrip.domain.repository.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;

public interface MemberRepository {

	public static final String SERVICE_NAME = "memberRepository";

	public void save(TableMember member);

	public void updateStatus(String memberId, TableUserStatusEnum status);

	public void updateOrganizer(String tableUserId, Boolean isOrganizer);

	public TableMember findById(String memberId);

	public List<TableMember> find(String tripId, String tableId);

	public List<TableMember> find(String tripId, String tableId, int limit);

	public void removeById(String memberId);

	public void removeByTripId(String tripId);
}
