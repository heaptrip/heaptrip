package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;

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
	 * @param feedTripCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByFeedTripCriteria(FeedTripCriteria feedTripCriteria);

	/**
	 * Get trips list which suitable for search criteria
	 * 
	 * @param myAccountTripCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria);

	/**
	 * Get trips list which suitable for search criteria
	 * 
	 * @param foreignAccountTripCriteria
	 * @return trips list
	 */
	public List<Trip> getTripsByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param feedTripCriteria
	 * @return number of trips
	 */
	public long getTripsCountByFeedTripCriteria(FeedTripCriteria feedTripCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param myAccountTripCriteria
	 * @return number of trips
	 */
	public long getTripsCountByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria);

	/**
	 * Get number of trips which suitable for search criteria
	 * 
	 * @param foreignAccountTripCriteria
	 * @return number of trips
	 */
	public long getTripsCountByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria);

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
