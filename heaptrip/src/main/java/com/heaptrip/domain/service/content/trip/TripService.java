package com.heaptrip.domain.service.content.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;

/**
 * Basic service to work with trips
 * 
 */
public interface TripService extends ContentService {

	/**
	 * Save a new content
	 * 
	 * @param content
	 * @param locale
	 *            the locale for which to create the content
	 * @return content
	 */
	public Trip save(Trip content, Locale locale);

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
	 * Get table items by trip id
	 * 
	 * @param tripId
	 * @return table items
	 */
	public List<TableItem> getTableItems(String tripId);

	/**
	 * Get nearest trip from the timetable. It is recommended to use to display
	 * the time in the trip card
	 * 
	 * @param trip
	 * @return item of timetable
	 */
	public TableItem getNearestTableItem(Trip trip);

	/**
	 * Get nearest trip from the timetable which suitable for search period. It
	 * is recommended to use to display the time in the trip card
	 * 
	 * @param trip
	 * @param period
	 * @return item of timetable
	 */
	public TableItem getNearestTableItemByPeriod(Trip trip, SearchPeriod period);

	/**
	 * Get latest trip from the timetable by trip id. Should be used to
	 * determine the possibility of rating. Trip can be rated in six months with
	 * the launch of the last item of timetable
	 * 
	 * @param tripId
	 *            trip id
	 * @return item of timetable
	 */
	public TableItem getLatestTableItem(String tripId);

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
	 * Add post to trip
	 * 
	 * @param tripId
	 *            trip id
	 * @param postId
	 *            post id
	 */
	public void addPost(String tripId, String postId);

	/**
	 * Remove post from trip
	 * 
	 * @param tripId
	 *            trip id
	 * @param postId
	 *            posts id
	 */
	public void removePost(String tripId, String postId);
}
