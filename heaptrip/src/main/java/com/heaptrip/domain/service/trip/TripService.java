package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.RoutePhoto;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.SearchPeriod;

public interface TripService {

	/**
	 * Save a new trip
	 * 
	 * @param trip
	 * @return tripid
	 */
	public String saveTrip(Trip trip);

	/**
	 * Soft remove trip
	 * 
	 * @param tripId
	 * @param ownerId
	 */
	public void removeTrip(String tripId, String ownerId);

	/**
	 * Hard remove trip. It is recommended to use the after tests to clear data
	 * 
	 * @param tripId
	 */
	public void hardRemoveTrip(String tripId);

	/**
	 * Get trips list which suitable for search criteria
	 * 
	 * @param tripCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param tripCriteria
	 * @return number of trips
	 */
	public long getTripsCountByCriteria(TripCriteria tripCriteria);

	/**
	 * Get nearest trip from the timetable
	 * 
	 * @param trip
	 * @return item of timetable
	 */
	public TableItem getNearTableItem(Trip trip);

	/**
	 * Get nearest trip from the timetable which suitable for search period
	 * 
	 * @param trip
	 * @param period
	 * @return item of timetable
	 */
	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period);

	/**
	 * Set status of trip
	 * 
	 * @param tripId
	 * @param ownerId
	 * @param status
	 */
	public void setTripStatus(String tripId, String ownerId, ContentStatusEnum status);

	/**
	 * Increase the views trip
	 * 
	 * @param tripId
	 */
	public void incTripViews(String tripId);

	/**
	 * Get information of the trip
	 * 
	 * @param tripId
	 * @param locale
	 * @return trip
	 */
	public Trip getTripInfo(String tripId, Locale locale);

	/**
	 * Update information of the trip
	 * 
	 * @param trip
	 * @param locale
	 */
	public void updateTripInfo(Trip trip, Locale locale);

	/**
	 * Get user from table item.Should be used to determine the set of possible
	 * user actions in the schedule
	 * 
	 * @param tableItem
	 * @param userId
	 * @return table user
	 */
	public TableUser getUserFromTableItem(TableItem tableItem, String userId);

	/**
	 * Send an invitation to registered users
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void addTableInvite(String tripId, String tableItemId, String userId);

	/**
	 * Send an invitation to an external email address
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param invite
	 */
	public void addTableInviteToEmail(String tripId, String tableItemId, String email);

	/**
	 * Send a user's request to participate in travel
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void addTableRequest(String tripId, String tableItemId, String userId);

	/**
	 * Accept user to the members trip. Needs to be called when the user accepts
	 * the invitation to participate, or when the owner accepts the request from
	 * a user to participate in travel
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void acceptTableUser(String tripId, String tableItemId, String userId);

	/**
	 * Remove the user from the travel. Needs to be called when the user reject
	 * the invitation to participate, as well as the when owner rejects the
	 * request from the user to participate in travel or when the owner removes
	 * the participant travel
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void removeTableUser(String tripId, String tableItemId, String userId);

	/**
	 * Remove table invite to email
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param tableInviteId
	 */
	public void removeTableInviteToEmail(String tripId, String tableItemId, String tableInviteId);

	/**
	 * Set the trip organizer
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 * @param isOrganizer
	 */
	public void setTableUserOrganizer(String tripId, String tableItemId, String userId, Boolean isOrganizer);

	/***/
	// TODO

	/**
	 * добавить пользователя в список разрешенных к просмотру во все путешествия
	 * заданного автора вызывать при добавлении друга
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void addAllowed(String ownerId, String userId);

	/**
	 * удалить из списка разрешенных пользователей
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void removeAllowed(String ownerId, String userId);

	public void saveRouteDescription(String tripId, String description, Locale locale);

	public String getRouteDescription(String tripId, Locale locale);

	public List<RoutePhoto> getRoutePhotos(String tripId);

	public void addTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void updateTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void removeTripRoutePhoto(String tripId, RoutePhoto routePhoto);
}
