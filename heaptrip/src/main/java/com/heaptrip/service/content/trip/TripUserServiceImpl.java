package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripInvite;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.trip.TripException;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import com.heaptrip.domain.service.system.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TripUserServiceImpl implements TripUserService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TripMemberRepository tripMemberRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public TripUser sendInvite(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");

        boolean isMember = isTripTableMember(tripId, tableId, userId);
        if (isMember) {
            throw errorService.createException(TripException.class, ErrorEnum.ERROR_TRIP_USER_ALREADY_ADDED);
        }

        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.INVITE);
        tableUser = tripMemberRepository.save(tableUser);

        tripRepository.incTableMembers(tripId, tableId, 1);

        TripNotification notification = new TripNotification();
        notification.setFromId(contentRepository.getOwnerId(tripId));
        notification.setToId(userId);
        notification.setType(NotificationTypeEnum.TRIP_INNER_INVITE);
        notification.setContentId(tripId);
        notification.setTableId(tableId);
        notificationService.addNotification(notification);

        return tableUser;
    }

    @Override
    public TripInvite sendExternalInvite(String tripId, String tableId, String email) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(email, "email must not be null");

        boolean isMember = tripMemberRepository.existsByTripIdAndTableIdAndEmail(tripId, tableId, email);
        if (isMember) {
            throw errorService.createException(TripException.class, ErrorEnum.ERROR_TRIP_USER_ALREADY_ADDED);
        }

        TripInvite invite = new TripInvite();
        invite.setContentId(tripId);
        invite.setTableId(tableId);
        invite.setEmail(email);
        invite = tripMemberRepository.save(invite);

        tripRepository.incTableMembers(tripId, tableId, 1);

        return invite;
    }

    @Override
    public TripUser sendRequest(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");

        boolean isMember = isTripTableMember(tripId, tableId, userId);
        if (isMember) {
            throw errorService.createException(TripException.class, ErrorEnum.ERROR_TRIP_USER_ALREADY_ADDED);
        }

        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.REQUEST);
        tableUser = tripMemberRepository.save(tableUser);

        tripRepository.incTableMembers(tripId, tableId, 1);

        TripNotification notification = new TripNotification();
        notification.setFromId(userId);
        notification.setToId(contentRepository.getOwnerId(tripId));
        notification.setType(NotificationTypeEnum.TRIP_REQUEST);
        notification.setContentId(tripId);
        notification.setTableId(tableId);
        notificationService.addNotification(notification);

        return tableUser;
    }

    @Override
    public void acceptTripMember(String memberId) {
        Assert.notNull(memberId, "memberId must not be null");
        tripMemberRepository.setStatus(memberId, TableUserStatusEnum.OK);
    }

    @Override
    public void acceptTripMember(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");

        tripMemberRepository.setStatusByTripIdAndTableIdAndUserId(tripId, tableId, userId, TableUserStatusEnum.OK);
    }

    @Override
    public boolean isTripAcceptedMember(String tripId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return tripMemberRepository.existsByTripIdAndUserIdAndStatusOk(tripId, userId);
    }

    @Override
    public boolean isTripTableMember(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return tripMemberRepository.existsByTripIdAndTableIdAndUserId(tripId, tableId, userId);
    }

    @Override
    public void setTripMemberOrganizer(String memberId, Boolean isOrganizer) {
        Assert.notNull(memberId, "memberId must not be null");
        tripMemberRepository.setOrganizer(memberId, isOrganizer);
    }

    @Override
    public List<TripMember> getMembersByCriteria(TripMemberCriteria tripMemberCriteria) {
        Assert.notNull(tripMemberCriteria, "criteria must not be null");
        Assert.notNull(tripMemberCriteria.getTripId(), "criteria.tripId must not be null");
        return tripMemberRepository.findByCriteria(tripMemberCriteria);
    }

    @Override
    public long getCountByCriteria(TripMemberCriteria tripMemberCriteria) {
        Assert.notNull(tripMemberCriteria, "criteria must not be null");
        Assert.notNull(tripMemberCriteria.getTripId(), "criteria.tripId must not be null");
        return tripMemberRepository.countByCriteria(tripMemberCriteria);
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
    public void removeTripMember(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");
        tripRepository.incTableMembers(tripId, tableId, -1);
        tripMemberRepository.removeByTripIdAndTableIdAndUserId(tripId, tableId, userId);
    }

    @Override
    public void removeTripMembers(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        tripRepository.resetMembers(tripId);
        tripMemberRepository.removeByTripId(tripId);
    }

}
