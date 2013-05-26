package com.heaptrip.service.trip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.trip.TableInvite;
import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.trip.TripUserService;

@Service
public class TripUserServiceImpl implements TripUserService {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public void addTableUser(String tripId, String tableId, String userId) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		Assert.notNull(userId, "userId");
		TableUser tableUser = new TableUser();
		tableUser.setTripId(tripId);
		tableUser.setTableId(tableId);
		tableUser.setUserId(userId);
		tableUser.setStatus(TableUserStatusEnum.INVITE);
		// TODO read name and photo from profile
		memberRepository.save(tableUser);
		tripRepository.incTableMembers(tripId, tableId, 1);
	}

	@Override
	public void addTableInvite(String tripId, String tableId, String email) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		Assert.notNull(email, "email");
		TableInvite invite = new TableInvite();
		invite.setTripId(tripId);
		invite.setTableId(tableId);
		invite.setEmail(email);
		memberRepository.save(invite);
		tripRepository.incTableMembers(tripId, tableId, 1);
	}

	@Override
	public void addTableRequest(String tripId, String tableId, String userId) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		Assert.notNull(userId, "userId");
		TableUser tableUser = new TableUser();
		tableUser.setTripId(tripId);
		tableUser.setTableId(tableId);
		tableUser.setUserId(userId);
		tableUser.setStatus(TableUserStatusEnum.REQUEST);
		// TODO read name and photo from profile
		memberRepository.save(tableUser);
		tripRepository.incTableMembers(tripId, tableId, 1);
	}

	@Override
	public void acceptTableUser(String tableUserId) {
		Assert.notNull(tableUserId, "tableUserId");
		memberRepository.setStatus(tableUserId, TableUserStatusEnum.OK);
	}

	@Override
	public void setTableUserOrganizer(String tableUserId, Boolean isOrganizer) {
		Assert.notNull(tableUserId, "tableUserId");
		memberRepository.setOrganizer(tableUserId, isOrganizer);
	}

	@Override
	public List<TableMember> getTableMembers(String tripId, String tableId, int limit) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		return memberRepository.findByTable(tripId, tableId, limit);
	}

	@Override
	public List<TableMember> getTableMembers(String tripId, String tableId) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		return memberRepository.findByTable(tripId, tableId);
	}

	@Override
	public void removeTripMember(String memberId) {
		Assert.notNull(memberId, "memberId");
		TableMember member = memberRepository.findById(memberId);
		Assert.notNull(member, "error memberId");
		memberRepository.removeById(memberId);
		if (member.getTripId() != null && member.getTableId() != null) {
			tripRepository.incTableMembers(member.getTripId(), member.getTableId(), -1);
		}
	}

	@Override
	public void removeTripMembers(String tripId) {
		Assert.notNull(tripId, "tripId");
		memberRepository.removeByTripId(tripId);
	}

	@Override
	public void addAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId");
		Assert.notNull(userId, "userId");
		memberRepository.addAllowed(ownerId, userId);
	}

	@Override
	public void removeAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId");
		Assert.notNull(userId, "userId");
		memberRepository.removeAllowed(ownerId, userId);
	}
}
