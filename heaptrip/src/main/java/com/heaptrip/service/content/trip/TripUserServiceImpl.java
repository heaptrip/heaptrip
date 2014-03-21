package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripInvite;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.trip.TripUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TripUserServiceImpl implements TripUserService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMemberRepository tripMemberRepository;

    @Override
    public TripUser addTripUser(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");
        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.INVITE);
        tripRepository.incTableMembers(tripId, tableId, 1);
        return tripMemberRepository.save(tableUser);
    }

    @Override
    public TripInvite addTripInvite(String tripId, String tableId, String email) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(email, "email must not be null");
        TripInvite invite = new TripInvite();
        invite.setContentId(tripId);
        invite.setTableId(tableId);
        invite.setEmail(email);
        tripRepository.incTableMembers(tripId, tableId, 1);
        return tripMemberRepository.save(invite);
    }

    @Override
    public TripUser addTripRequest(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");
        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.REQUEST);
        tripRepository.incTableMembers(tripId, tableId, 1);
        return tripMemberRepository.save(tableUser);
    }

    @Override
    public void acceptTripUser(String memberId) {
        Assert.notNull(memberId, "memberId must not be null");
        tripMemberRepository.setStatus(memberId, TableUserStatusEnum.OK);
    }

    @Override
    public void setTripUserOrganizer(String memberId, Boolean isOrganizer) {
        Assert.notNull(memberId, "memberId must not be null");
        tripMemberRepository.setOrganizer(memberId, isOrganizer);
    }

    @Override
    public List<TripMember> getTripMembers(String tripId, String tableId, int limit) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        // TODO konovalov: add sort
        return tripMemberRepository.findByTripIdAndTableId(tripId, tableId, limit);
    }

    @Override
    public List<TripMember> getTripMembers(String tripId, String tableId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        // TODO konovalov: add sort
        return tripMemberRepository.findByTripIdAndTableId(tripId, tableId);
    }

    @Override
    public List<TripUser> getTripUsersByUserId(String tripId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return tripMemberRepository.findByUserId(tripId, userId);
    }

    @Override
    public boolean isTripUser(String tripId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return tripMemberRepository.existsByTripIdAndUserId(tripId, userId);
    }

    @Override
    public void removeTripMember(String memberId) {
        Assert.notNull(memberId, "memberId must not be null");
        TripMember member = tripMemberRepository.findOne(memberId);
        Assert.notNull(member, "error memberId: " + memberId);
        if (member.getContentId() != null && member.getTableId() != null) {
            tripRepository.incTableMembers(member.getContentId(), member.getTableId(), -1);
        }
        tripMemberRepository.remove(memberId);
    }

    @Override
    public void removeTripMembers(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        tripMemberRepository.removeByTripId(tripId);
    }
}
