package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface TripRepository {

	public void save(Trip trip);

	public Trip findById(String tripId);

	public void removeById(String tripId);

	public void setDeleted(String tripId);

	public List<Trip> findByFeedCriteria(TripCriteria criteria);

	public List<Trip> findByMyAccountCriteria(TripCriteria criteria);

	public List<Trip> findByNotMyAccountCriteria(TripCriteria criteria);

	public List<Trip> findByMemberCriteria(TripCriteria criteria);

	public List<Trip> findByFavoritesCriteria(TripCriteria criteria);

	public long getCountByFeedCriteria(TripCriteria criteria);

	public long getCountByMyAccountCriteria(TripCriteria criteria);

	public long getCountByNotMyAccountCriteria(TripCriteria criteria);

	public long getCountByMemberCriteria(TripCriteria criteria);

	public long getCountByFavoritesCriteria(TripCriteria criteria);

	public Trip getInfo(String tripId, Locale locale);

	public void update(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);
}
