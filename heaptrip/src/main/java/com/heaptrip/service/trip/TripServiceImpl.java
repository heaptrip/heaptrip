package com.heaptrip.service.trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.trip.RoutePhoto;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;

@Service
public class TripServiceImpl implements TripService {

	@Override
	public String addTrip(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAllowed(String ownerId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllowed(String ownerId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrip(String tripId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTripStatus(String tripId, ContentStatusEnum status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTripInfo(Trip trip, Locale locale) {
		// TODO Auto-generated method stub

	}

	@Override
	public Trip getTripInfo(String tripId, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTableItem(String tripId, TableItem tableItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableItem(String tripId, String tableItemId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelTableItem(String tripId, String tableItemId, String cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void abortTableItem(String tripId, String tableItemId, String cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableInvite(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableInviteByEmail(String tripId, String tableItemId, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refuseTableInvite(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptTableInvite(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableRequest(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectTableRequest(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableUser(String tripId, String tableItemId, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTableUserOrganizer(String tripId, String tableItemId, String userId, Boolean isOrganizer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveRouteDescription(String tripId, String description, Locale locale) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRouteDescription(String tripId, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoutePhoto> getRoutePhotos(String tripId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTripRoutePhoto(String tripId, RoutePhoto routePhoto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTripRoutePhoto(String tripId, RoutePhoto routePhoto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTripRoutePhoto(String tripId, RoutePhoto routePhoto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria) {
		List<Trip> trips = new ArrayList<Trip>();

		int limit = 3;

		if (tripCriteria.getLimit() != null) {
			limit = tripCriteria.getLimit().intValue();
		}

		for (int i = 0; i < limit; i++) {
			Trip trip = new Trip();
			MultiLangText name = new MultiLangText();
			name.setTextRu("Наименовани " + i);
			name.setTextEn("Name " + i);
			trip.setName(name);
			trips.add(trip);
		}

		return trips;
	}

	@Override
	public Long getTripsCountByCriteria(TripCriteria tripCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableItem getNearTableItem(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hardRemoveTrip(String tripId) {
		// TODO Auto-generated method stub

	}

}
