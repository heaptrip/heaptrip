package com.heaptrip.domain.service.content.trip;

import java.util.List;

import com.heaptrip.domain.entity.content.trip.TripInvite;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;

/**
 * 
 * Service to work with user of the trip
 * 
 */
public interface TripUserService {

	/**
	 * Send an invitation to registered users
	 * 
	 * @param tripId
	 * @param tableId
	 * @param userId
	 * @return table user with status INVITE
	 */
	public TripUser addTripUser(String tripId, String tableId, String userId);

	/**
	 * Send an invitation to an external email address
	 * 
	 * @param tripId
	 * @param tableId
	 * @param invite
	 * @return table invite
	 */
	public TripInvite addTripInvite(String tripId, String tableId, String email);

	/**
	 * Send a user's request to participate in trip
	 * 
	 * @param tripId
	 * @param tableId
	 * @param userId
	 * @return table user with status REQUEST
	 */
	public TripUser addTripRequest(String tripId, String tableId, String userId);

	/**
	 * Accept user to the members trip. Needs to be called when the user accepts
	 * the invitation to participate, or when the owner accepts the request from
	 * a user to participate in travel
	 * 
	 * @param memberId
	 */
	public void acceptTripUser(String memberId);

	/**
	 * Set trip organizer
	 * 
	 * @param memberId
	 * @param isOrganizer
	 */
	public void setTripUserOrganizer(String memberId, Boolean isOrganizer);

	/**
	 * Get all members for table item without limit
	 * 
	 * @param tripId
	 * @param tableId
	 * @return member list
	 */
	public List<TripMember> getTripMembers(String tripId, String tableId);

	/**
	 * Get limit members for table item
	 * 
	 * @param tripId
	 * @param tableId
	 * @param limit
	 * @return members list
	 */
	public List<TripMember> getTripMembers(String tripId, String tableId, int limit);

	/**
	 * Get list of TripUser by tripId and userId. Must be called to determine
	 * the list of possible actions in the schedule of trip.
	 * 
	 * @param tripId
	 * @param userId
	 * @return list of TableUser
	 */
	public List<TripUser> getTripUsersByUserId(String tripId, String userId);

	/**
	 * Check that the user is a the trip member
	 * 
	 * @param tripId
	 *            trip id
	 * @param userId
	 *            user id
	 * @return true or false
	 */
	public boolean isTripUser(String tripId, String userId);

	/**
	 * Remove the user or invite from the travel. Needs to be called when the
	 * user reject the invitation to participate, as well as the when owner
	 * rejects the request from the user to participate in travel, as well as
	 * the when the owner removes user or when remove invite to email
	 * 
	 * @param memberId
	 */
	public void removeTripMember(String memberId);

	/**
	 * Remove all trips members. It is recommended to use the after trips tests
	 * to clear data
	 * 
	 * @param tripId
	 */
	public void removeTripMembers(String tripId);
}
