package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.RoutePhoto;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.SearchPeriod;

public interface TripService {

	/**
	 * Add a new trip
	 * 
	 * @param trip
	 * @return tripid
	 */
	public String addTrip(Trip trip);

	/**
	 * Soft remove trip
	 * 
	 * @param tripId
	 */
	public void removeTrip(String tripId);

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
	public Long getTripsCountByCriteria(TripCriteria tripCriteria);

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
	 * @param status
	 */
	public void setTripStatus(String tripId, ContentStatusEnum status);

	/**
	 * Update information of the trip
	 * 
	 * @param trip
	 * @param locale
	 */
	public void updateTripInfo(Trip trip, Locale locale);

	/**
	 * Get information of the trip
	 * 
	 * @param tripId
	 * @param locale
	 * @return trip
	 */
	public Trip getTripInfo(String tripId, Locale locale);

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

	public void addTableItem(String tripId, TableItem tableItem);

	public void removeTableItem(String tripId, String tableItemId);

	public void cancelTableItem(String tripId, String tableItemId, String cause);

	public void abortTableItem(String tripId, String tableItemId, String cause);

	/**
	 * отправить приглашение
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void addTableInvite(String tripId, String tableItemId, String userId);

	/**
	 * отправить инвайт на электронный адрес
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param invite
	 */
	public void addTableInviteByEmail(String tripId, String tableItemId, String email);

	/**
	 * отказаться от приглашения
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void refuseTableInvite(String tripId, String tableItemId, String userId);

	/**
	 * принять приглашение
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void acceptTableInvite(String tripId, String tableItemId, String userId);

	/**
	 * отправить запрос на участие
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void addTableRequest(String tripId, String tableItemId, String userId);

	/**
	 * отклонить запрос на участие
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void rejectTableRequest(String tripId, String tableItemId, String userId);

	/**
	 * удалить участника
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void removeTableUser(String tripId, String tableItemId, String userId);

	/**
	 * указать организатора путешествия
	 * 
	 * @param tripId
	 * @param tableItemId
	 * @param userId
	 */
	public void setTableUserOrganizer(String tripId, String tableItemId, String userId, Boolean isOrganizer);

	//
	public void saveRouteDescription(String tripId, String description, Locale locale);

	public String getRouteDescription(String tripId, Locale locale);

	public List<RoutePhoto> getRoutePhotos(String tripId);

	public void addTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void updateTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void removeTripRoutePhoto(String tripId, RoutePhoto routePhoto);
}
