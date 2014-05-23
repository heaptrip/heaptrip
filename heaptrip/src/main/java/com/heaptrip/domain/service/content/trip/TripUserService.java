package com.heaptrip.domain.service.content.trip;

import com.heaptrip.domain.entity.content.trip.TripInvite;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;

import java.util.List;

/**
 * Service to work with user of the trip
 */
public interface TripUserService {

    /**
     * Send an invitation to registered users
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param userId  user id
     * @return table user with status INVITE
     */
    public TripUser sendInvite(String tripId, String tableId, String userId);

    /**
     * Send an invitation to an external email address
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param email   users email
     * @return table invite
     */
    public TripInvite sendExternalInvite(String tripId, String tableId, String email);

    /**
     * Send a user's request to participate in trip
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param userId  user id
     * @return table user with status REQUEST
     */
    public TripUser sendRequest(String tripId, String tableId, String userId);

    /**
     * Accept user to the members trip. Needs to be called when the user accepts
     * the invitation to participate, or when the owner accepts the request from
     * a user to participate in travel. This method increments the members travel.
     *
     * @param memberId table member id
     */
    public void acceptTripMember(String memberId);

    /**
     * Accept user to the members trip. Needs to be called when the user accepts
     * the invitation to participate, or when the owner accepts the request from
     * a user to participate in travel. This method increments the members travel.
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param userId  user id
     */
    public void acceptTripMember(String tripId, String tableId, String userId);

    /**
     * Check that the user is a the trip member
     *
     * @param tripId trip id
     * @param userId user id
     * @return true or false
     */
    public boolean isTripAcceptedMember(String tripId, String userId);

    /**
     * Check that the user is a the table member
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param userId  user id
     * @return true or false
     */
    public boolean isTripTableMember(String tripId, String tableId, String userId);

    /**
     * Set trip organizer
     *
     * @param memberId    table member id
     * @param isOrganizer sign that the member is a trip organizer
     */
    public void setTripMemberOrganizer(String memberId, Boolean isOrganizer);

    /**
     * Get trip members by criteria
     *
     * @param tripMemberCriteria criteria for search trip members
     * @return member list
     */
    public List<TripMember> getMembersByCriteria(TripMemberCriteria tripMemberCriteria);

    /**
     * Get trip members count by criteria
     *
     * @param tripMemberCriteria criteria for search trip members
     * @return count of members
     */
    public long getCountByCriteria(TripMemberCriteria tripMemberCriteria);

    /**
     * Remove the user or invite from the travel. Needs to be called when the
     * user reject the invitation to participate, as well as the when owner
     * rejects the request from the user to participate in travel, as well as
     * the when the owner removes user or when remove invite to email
     *
     * @param memberId table member id
     */
    public void removeTripMember(String memberId);

    /**
     * Remove the user or invite from the travel. Needs to be called when the
     * user reject the invitation to participate, as well as the when owner
     * rejects the request from the user to participate in travel, as well as
     * the when the owner removes user
     *
     * @param tripId  trip id
     * @param tableId table id
     * @param userId  user id
     */
    public void removeTripMember(String tripId, String tableId, String userId);

    /**
     * Remove all trips members. It is recommended to use the after trips tests
     * to clear data
     *
     * @param tripId trip id
     */
    public void removeTripMembers(String tripId);
}
