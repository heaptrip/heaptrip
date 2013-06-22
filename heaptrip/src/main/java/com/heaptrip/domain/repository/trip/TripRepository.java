package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;
import com.heaptrip.domain.service.trip.MyAccountTripCriteria;

public interface TripRepository extends CrudRepository<Trip> {

	public void setDeleted(String tripId);

	public List<Trip> findByFeedTripCriteria(FeedTripCriteria criteria);

	public List<Trip> findByMyAccountTripCriteria(MyAccountTripCriteria criteria);

	public List<Trip> findByForeignAccountTripCriteria(ForeignAccountTripCriteria criteria);

	public long getCountByFeedTripCriteria(FeedTripCriteria criteria);

	public long getCountByMyAccountTripCriteria(MyAccountTripCriteria criteria);

	public long getCountByForeignAccountTripCriteria(ForeignAccountTripCriteria criteria);

	public Trip getInfo(String tripId, Locale locale);

	public void update(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);
}
