package com.heaptrip.domain.repository.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;
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

	public long getCountFindForMyAccountByCriteria(TripCriteria criteria);

	public long getCountFindForNotMyAccountByCriteria(TripCriteria criteria);

	public long getCountFindForMemberByCriteria(TripCriteria criteria);

	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed);

	public void incViews(String tripId);

	public Trip getTripInfo(String tripId, Locale locale);

	public void update(Trip trip, Locale locale);

	public void incTableUsers(String tripId, String tableId, int value);

	public void incTableInvites(String tripId, String tableId, int value);

	public void removeTableUser(String tripId, String tableItemId, String userId);

	public void updateTableUser(String tripId, String tableItemId, String userId, TableUserStatusEnum status);
}
