package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;

public interface TripRepository extends CrudRepository<Trip> {

	public void setDeleted(String tripId);

	public List<Trip> findByFeedTripCriteria(TripFeedCriteria criteria);

	public List<Trip> findByMyAccountTripCriteria(TripMyAccountCriteria criteria);

	public List<Trip> findByForeignAccountTripCriteria(TripForeignAccountCriteria criteria);

	public long getCountByFeedTripCriteria(TripFeedCriteria criteria);

	public long getCountByMyAccountTripCriteria(TripMyAccountCriteria criteria);

	public long getCountByForeignAccountTripCriteria(TripForeignAccountCriteria criteria);

	public Trip getInfo(String tripId, Locale locale);

	public void updateInfo(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);

	public Trip getMainLanguage(String tripId);

	public void removeLanguage(String tripId, Locale locale);

	public Trip getRoute(String tripId, Locale locale);

	public void updateRoute(Trip trip, Locale locale);
}
