package com.heaptrip.domain.service.content.post;

import java.util.Locale;

import com.heaptrip.domain.entity.content.trip.Trip;

/**
 * Basic service to work with posts
 * 
 */
public interface PostService {

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

}
