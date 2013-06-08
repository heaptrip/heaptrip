package com.heaptrip.domain.service.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.Image;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.SearchPeriod;

/**
 * Basic service to work with trips
 * 
 */
public interface TripService {

	/**
	 * Save a new trip
	 * 
	 * @param trip
	 * @return tripId
	 */
	public String saveTrip(Trip trip);

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
	public long getTripsCountByCriteria(TripCriteria tripCriteria);

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

	/**
	 * Save image to GridFS
	 * 
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return image
	 * @throws IOException
	 */
	public Image saveImage(String fileName, InputStream is) throws IOException;
}
