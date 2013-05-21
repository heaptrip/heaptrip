package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableStatus;
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

	public List<Trip> findForMemberByCriteria(TripCriteria criteria);

	public long getCountForFeedByCriteria(TripCriteria criteria);

	public long getCountForMyAccountByCriteria(TripCriteria criteria);

	public long getCountForNotMyAccountByCriteria(TripCriteria criteria);

	public long getCountForMemberByCriteria(TripCriteria criteria);

	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed);

	public void incViews(String tripId);

	public Trip getInfo(String tripId, Locale locale);

	public void update(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);
}
