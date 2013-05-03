package com.heaptrip.domain.repository.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface TripRepository {

	public static final String SERVICE_NAME = "tripRepository";

	public void save(Trip trip);

	public Trip findById(String tripId);

	public void removeTrip(String tripId);

	public void setDeleted(String tripId, String ownerId);

	public List<Trip> findForFeedByCriteria(TripCriteria criteria);

	public List<Trip> findForMyAccountByCriteria(TripCriteria criteria);

	public List<Trip> findForNotMyAccountByCriteria(TripCriteria criteria);

	public long getCountForFeedByCriteria(TripCriteria criteria);

	public long getCountFindForMyAccountByCriteria(TripCriteria criteria);

	public long getCountFindForNotMyAccountByCriteria(TripCriteria criteria);
}
