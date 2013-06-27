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
	public TableUser addTableUser(String tripId, String tableId, String userId) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		Assert.notNull(userId, "userId must not be null");
		TableUser tableUser = new TableUser();
		tableUser.setTripId(tripId);
		tableUser.setTableId(tableId);
		tableUser.setUserId(userId);
		tableUser.setStatus(TableUserStatusEnum.INVITE);
		// TODO read name and image from profile
		tripRepository.incTableMembers(tripId, tableId, 1);
		return memberRepository.save(tableUser);
	}

	@Override
	public TableInvite addTableInvite(String tripId, String tableId, String email) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		Assert.notNull(email, "email must not be null");
		TableInvite invite = new TableInvite();
		invite.setTripId(tripId);
		invite.setTableId(tableId);
		invite.setEmail(email);
		tripRepository.incTableMembers(tripId, tableId, 1);
		return memberRepository.save(invite);
	}

	@Override
	public TableUser addTableRequest(String tripId, String tableId, String userId) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		Assert.notNull(userId, "userId must not be null");
		TableUser tableUser = new TableUser();
		tableUser.setTripId(tripId);
		tableUser.setTableId(tableId);
		tableUser.setUserId(userId);
		tableUser.setStatus(TableUserStatusEnum.REQUEST);
		// TODO read name and image from profile
		tripRepository.incTableMembers(tripId, tableId, 1);
		return memberRepository.save(tableUser);
	}

	@Override
	public void acceptTableUser(String tableUserId) {
		Assert.notNull(tableUserId, "tableUserId must not be null");
		memberRepository.setStatus(tableUserId, TableUserStatusEnum.OK);
	}

	@Override
	public void setTableUserOrganizer(String tableUserId, Boolean isOrganizer) {
		Assert.notNull(tableUserId, "tableUserId must not be null");
		memberRepository.setOrganizer(tableUserId, isOrganizer);
	}

	@Override
	public List<TableMember> getTableMembers(String tripId, String tableId, int limit) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		return memberRepository.findByTripIdAndTableId(tripId, tableId, limit);
	}

	@Override
	public List<TableMember> getTableMembers(String tripId, String tableId) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		return memberRepository.findByTripIdAndTableId(tripId, tableId);
	}

	@Override
	public List<TableUser> getTableUsersByUserId(String tripId, String userId) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(userId, "userId must not be null");
		return memberRepository.findTableUsersByUserId(tripId, userId);
	}

	@Override
	public void removeTripMember(String memberId) {
		Assert.notNull(memberId, "memberId must not be null");
		TableMember member = memberRepository.findOne(memberId);
		Assert.notNull(member, "error memberId: " + memberId);
		memberRepository.remove(memberId);
		if (member.getTripId() != null && member.getTableId() != null) {
			tripRepository.incTableMembers(member.getTripId(), member.getTableId(), -1);
		}
	}

	@Override
	public void removeTripMembers(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		memberRepository.removeByTripId(tripId);
	}

	@Override
	public void addAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(userId, "userId must not be null");
		memberRepository.addAllowed(ownerId, userId);
	}

	@Override
	public void removeAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(userId, "userId must not be null");
		memberRepository.removeAllowed(ownerId, userId);
	}
}
