package com.heaptrip.service.trip;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.entity.ContentStatus;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.RoutePhoto;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TripRepository tripRepository;

	@Override
	public String saveTrip(Trip trip) {
		Assert.notNull(trip, "trip");
		Assert.notNull(trip.getOwner(), "owner");
		Assert.notNull(trip.getOwner().getId(), "owner.id");
		Assert.notEmpty(trip.getName(), "name");
		Assert.notEmpty(trip.getLangs(), "langs");
		Assert.notEmpty(trip.getSummary(), "summary");
		Assert.notEmpty(trip.getDescription(), "description");

		// TODO
		// read and set owner name, account type and rating
		// if owner account type == (CLUB or COMPANY) then set owners
		trip.setOwners(new String[] { trip.getOwner().getId() });

		if (trip.getCategories() != null) {
			for (ContentCategory contentCategory : trip.getCategories()) {
				Assert.notNull(contentCategory.getId(), "category.id");
				Category category = categoryRepository.findById(contentCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", contentCategory.getId()));
				contentCategory.setName(category.getName());
			}
		}

		// TODO
		// as well as categories read and set regions name

		trip.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
		trip.setCreated(new Date());
		trip.setDeleted(null);
		trip.setViews(0L);
		trip.setRating(0d);
		trip.setComments(0L);

		tripRepository.save(trip);

		return trip.getId();
	}

	@Override
	public void removeTrip(String tripId, String ownerId) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(ownerId, "ownerId");
		tripRepository.setTripDeleted(tripId, ownerId);
	}

	@Override
	public void hardRemoveTrip(String tripId) {
		Assert.notNull(tripId, "tripId");
		tripRepository.removeTrip(tripId);
	}

	@Override
	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria) {
		Assert.notNull(tripCriteria, "tripCriteria");

		if (tripCriteria.getMemberId() != null && !tripCriteria.getMemberId().isEmpty()) {
			// TODO find by memberId throw user profile service
		} else if (tripCriteria.getOwnerId() != null && !tripCriteria.getOwnerId().isEmpty()) {
			// find by ownerId
		} else {
			// feed
			return tripRepository.findTripByCriteria(tripCriteria);
		}

		//
		// List<Trip> trips = new ArrayList<Trip>();
		//
		// int limit = 3;
		//
		// if (tripCriteria.getLimit() != null) {
		// limit = tripCriteria.getLimit().intValue();
		// }
		//
		// for (int i = 0; i < limit; i++) {
		// Trip trip = new Trip();
		// MultiLangText name = new MultiLangText("Наименовани " + i, "Name " +
		// i);
		// trip.setName(name);
		// trips.add(trip);
		// }

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

}
