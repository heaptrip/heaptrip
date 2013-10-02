package com.heaptrip.domain.repository.content.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.TableStatus;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;

public interface TripRepository extends CrudRepository<Trip> {

	public List<Trip> findByFeedCriteria(TripFeedCriteria criteria);

	public List<Trip> findByMyAccountCriteria(TripMyAccountCriteria criteria);

	public List<Trip> findByForeignAccountCriteria(TripForeignAccountCriteria criteria);

	public long getCountByFeedCriteria(TripFeedCriteria criteria);

	public long getCountByMyAccountCriteria(TripMyAccountCriteria criteria);

	public long getCountByForeignAccountCriteria(TripForeignAccountCriteria criteria);

	public Trip getInfo(String tripId, Locale locale);

	public void updateInfo(Trip trip, Locale locale);

	public void incTableMembers(String tripId, String tableId, int value);

	public void setTableStatus(String tripId, String tableId, TableStatus status);

	public Trip getMainLanguage(String tripId);

	public void removeLanguage(String tripId, Locale locale);

	@Deprecated
	public Trip getRoute(String tripId, Locale locale);

	@Deprecated
	public void updateRoute(Trip trip, Locale locale);

	public TableItem[] getTableItemsWithDateBeginAndDateEnd(String tripId);

	public void addPostId(String tripId, String postId);

	public void removePostId(String tripId, String postId);

	public String[] getPostIds(String tripId);
}
