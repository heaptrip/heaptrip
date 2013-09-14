package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;

public interface TripRepository extends CrudRepository<Trip> {

	public void setDeleted(String tripId);

	public List<Trip> findByTripFeedCriteria(TripFeedCriteria criteria);

	public List<Trip> findByTripMyAccountCriteria(TripMyAccountCriteria criteria);

	public List<Trip> findByTripForeignAccountCriteria(TripForeignAccountCriteria criteria);

	public long getCountByTripFeedCriteria(TripFeedCriteria criteria);

	public long getCountByTripMyAccountCriteria(TripMyAccountCriteria criteria);

	public long getCountByTripForeignAccountCriteria(TripForeignAccountCriteria criteria);

	public Trip getInfo(String tripId, Locale locale);

	public void updateInfo(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);

	public Trip getMainLanguage(String tripId);

	public void removeLanguage(String tripId, Locale locale);

	public Trip getRoute(String tripId, Locale locale);

	public void updateRoute(Trip trip, Locale locale);

	public TableItem[] getTableItemsWithDateBeginAndDateEnd(String tripId);
}
