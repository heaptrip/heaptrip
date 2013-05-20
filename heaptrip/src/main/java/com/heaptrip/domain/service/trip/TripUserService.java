package com.heaptrip.domain.service.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.TableMember;

/**
 * 
 * Service to work with user trip
 * 
 */
public interface TripUserService {

	/**
	 * Send an invitation to registered users
	 * 
	 * @param tripId
	 * @param tableId
	 * @param userId
	 */
	public void addTableUser(String tripId, String tableId, String userId);

	/**
	 * Send an invitation to an external email address
	 * 
	 * @param tripId
	 * @param tableId
	 * @param invite
	 */
	public void addTableInvite(String tripId, String tableId, String email);

	/**
	 * Send a user's request to participate in trip
	 * 
	 * @param tripId
	 * @param tableId
	 * @param userId
	 */
	public void addTableRequest(String tripId, String tableId, String userId);

	/**
	 * Accept user to the members trip. Needs to be called when the user accepts
	 * the invitation to participate, or when the owner accepts the request from
	 * a user to participate in travel
	 * 
	 * @param tableUserId
	 */
	public void acceptTableUser(String tableUserId);

	/**
	 * Set the trip organizer
	 * 
	 * @param tableUserId
	 * @param isOrganizer
	 */
	public void setTableUserOrganizer(String tableUserId, Boolean isOrganizer);

	/**
	 * Get all members for table item
	 * 
	 * @param tripId
	 * @param tableId
	 * @return list of members
	 */
	public List<TableMember> getTableMembers(String tripId, String tableId);

	/**
	 * Get limit members for table item
	 * 
	 * @param tripId
	 * @param tableId
	 * @param limit
	 * @return list of members
	 */
	public List<TableMember> getTableMembers(String tripId, String tableId, int limit);

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

	/**
	 * Add a user to the list of allowed to view all trip this owner. Must be
	 * called this method when adding friend owner
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void addAllowed(String ownerId, String userId);

	/**
	 * Remove the user from the list of allowed to view all travel this owner
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void removeAllowed(String ownerId, String userId);

}
