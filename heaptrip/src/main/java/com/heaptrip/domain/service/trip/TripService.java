package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;

/**
 * Basic service to work with trips
 * 
 */
public interface TripService {

	/**
	 * Save a new trip
	 * 
	 * @param trip
	 * @param locale
	 *            the locale for which to create the trip
	 * @return trip
	 */
	public Trip saveTrip(Trip trip, Locale locale);

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
	 * @param tripFeedCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByTripFeedCriteria(TripFeedCriteria tripFeedCriteria);

	/**
	 * Get trips list which suitable for search criteria
	 * 
	 * @param tripMyAccountCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByTripMyAccountCriteria(TripMyAccountCriteria tripMyAccountCriteria);

	/**
	 * Get trips list which suitable for search criteria
	 * 
	 * @param tripForeignAccountCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByTripForeignAccountCriteria(TripForeignAccountCriteria tripForeignAccountCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param tripFeedCriteria
	 * @return number of trips
	 */
	public long getTripsCountByTripFeedCriteria(TripFeedCriteria tripFeedCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param tripMyAccountCriteria
	 * @return number of trips
	 */
	public long getTripsCountByTripMyAccountCriteria(TripMyAccountCriteria tripMyAccountCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param tripForeignAccountCriteria
	 * @return number of trips
	 */
	public long getTripsCountByTripForeignAccountCriteria(TripForeignAccountCriteria tripForeignAccountCriteria);

	/**
	 * Get nearest trip from the timetable. It is recommended to use to display
	 * the time in the trip card
	 * 
	 * @param trip
	 * @return item of timetable
	 */
	public TableItem getNearTableItem(Trip trip);

	/**
	 * Get nearest trip from the timetable which suitable for search period. It
	 * is recommended to use to display the time in the trip card
	 * 
	 * @param trip
	 * @param period
	 * @return item of timetable
	 */
	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period);

	/**
	 * Get base information of the trip
	 * 
	 * @param tripId
	 * @param locale
	 * @return trip
	 */
	public Trip getTripInfo(String tripId, Locale locale);

	/**
	 * Update base information of the trip
	 * 
	 * @param trip
	 * @param locale
	 *            the locale for which to update the trip
	 */
	public void updateTripInfo(Trip trip, Locale locale);

	/**
	 * Remove trip locale
	 * 
	 * @param tripId
	 * @param locale
	 */
	public void removeTripLocale(String tripId, Locale locale);

	/**
	 * Abort table item
	 * 
	 * @param tripId
	 * @param tableId
	 * @param cause
	 */
	public void abortTableItem(String tripId, String tableId, String cause);

	/**
	 * 
	 * Cancel table item
	 * 
	 * @param tripId
	 * @param tableId
	 * @param cause
	 */
	public void cancelTableItem(String tripId, String tableId, String cause);
}
