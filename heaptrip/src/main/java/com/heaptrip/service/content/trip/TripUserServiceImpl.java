package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.notification.TripNotification;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.MemberEnum;
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
import com.heaptrip.domain.service.system.RequestScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService requestScopeService;

    @Override
    public TripUser sendInvite(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");

        boolean isMember = isTripTableMember(tripId, tableId, userId);
        if (isMember) {
            throw errorService.createException(TripException.class, ErrorEnum.ERROR_TRIP_USER_ALREADY_ADDED);
        }

        // save trip member
        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.INVITE);
        tableUser = tripMemberRepository.save(tableUser);

        // send notification only if the user adds another user (not itself)
        if (requestScopeService.getCurrentUser().getId().equals(userId)) {
            TripNotification notification = new TripNotification();
            notification.setFromId(contentRepository.getOwnerId(tripId));
            notification.setToId(userId);
            notification.setType(NotificationTypeEnum.TRIP_INNER_INVITE);
            notification.setContentId(tripId);
            notification.setTableId(tableId);
            notificationService.addNotification(notification);
        }

        tripRepository.incTableMembers(tripId, tableId, 1);
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

        // save trip member
        TripUser tableUser = new TripUser();
        tableUser.setContentId(tripId);
        tableUser.setTableId(tableId);
        tableUser.setUserId(userId);
        tableUser.setStatus(TableUserStatusEnum.REQUEST);
        tableUser = tripMemberRepository.save(tableUser);

        // send notification only if the user is not trip owner (not itself)
        String ownerId = contentRepository.getOwnerId(tripId);
        if (requestScopeService.getCurrentUser().getId().equals(ownerId)) {
            TripNotification notification = new TripNotification();
            notification.setFromId(userId);
            notification.setToId(ownerId);
            notification.setType(NotificationTypeEnum.TRIP_REQUEST);
            notification.setContentId(tripId);
            notification.setTableId(tableId);
            notificationService.addNotification(notification);
        }

        tripRepository.incTableMembers(tripId, tableId, 1);
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

        // change counter of members
        if (member.getContentId() != null && member.getTableId() != null) {
            tripRepository.incTableMembers(member.getContentId(), member.getTableId(), -1);
        }

        // send notification only if the owner does not remove itself
        if (member.getMemberType().equals(MemberEnum.TRIP_USER)) {
            TripUser tripUser = (TripUser) member;
            String userId = tripUser.getUserId();
            String ownerId = contentRepository.getOwnerId(tripUser.getContentId());

            if (!ownerId.equals(userId)) {
                TripNotification notification = new TripNotification();
                notification.setFromId(userId);
                notification.setToId(ownerId);

                User currentUser = requestScopeService.getCurrentUser();
                if (currentUser.getId().equals(userId)) {
                    notification.setType(NotificationTypeEnum.TRIP_MEMBER_REFUSE);
                } else {
                    notification.setType(NotificationTypeEnum.TRIP_REMOVE_MEMBER);
                }

                notification.setContentId(tripUser.getContentId());
                notification.setTableId(tripUser.getTableId());
                notificationService.addNotification(notification);
            }
        }

        // remove member entity
        tripMemberRepository.remove(memberId);
    }

    @Override
    public void removeTripMember(String tripId, String tableId, String userId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        Assert.notNull(userId, "userId must not be null");

        // change counter of members
        tripRepository.incTableMembers(tripId, tableId, -1);

        // send notification only if the owner does not remove itself
        String ownerId = contentRepository.getOwnerId(tripId);
        if (!ownerId.equals(userId)) {
            TripNotification notification = new TripNotification();
            notification.setFromId(userId);
            notification.setToId(ownerId);

            User currentUser = requestScopeService.getCurrentUser();
            if (currentUser.getId().equals(userId)) {
                notification.setType(NotificationTypeEnum.TRIP_MEMBER_REFUSE);
            } else {
                notification.setType(NotificationTypeEnum.TRIP_REMOVE_MEMBER);
            }

            notification.setContentId(tripId);
            notification.setTableId(tableId);
            notificationService.addNotification(notification);
        }

        // remove member entity
        tripMemberRepository.removeByTripIdAndTableIdAndUserId(tripId, tableId, userId);
    }

    @Override
    public void removeTripMembers(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        tripRepository.resetMembers(tripId);
        tripMemberRepository.removeByTripId(tripId);
    }

}
