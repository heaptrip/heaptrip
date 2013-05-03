package com.heaptrip.service.trip;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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
		tripRepository.setDeleted(tripId, ownerId);
	}

	@Override
	public void hardRemoveTrip(String tripId) {
		Assert.notNull(tripId, "tripId");
		tripRepository.removeTrip(tripId);
	}

	@Override
	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria) {
		Assert.notNull(tripCriteria, "tripCriteria");
		List<Trip> result = null;
		if (StringUtils.isNotBlank(tripCriteria.getMemberId())) {
			// TODO find by memberId throw user profile service
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isBlank(tripCriteria.getUserId())) {
				// my account
				// TODO read supported lang for user, not only current locale
				result = tripRepository.findForMyAccountByCriteria(tripCriteria);
			} else {
				// not my account
				// TODO if account type == user, then read supported lang for
				// user, not only current locale
				result = tripRepository.findForNotMyAccountByCriteria(tripCriteria);
			}
		} else {
			// feed
			result = tripRepository.findForFeedByCriteria(tripCriteria);
		}
		return result;
	}

	@Override
	public long getTripsCountByCriteria(TripCriteria tripCriteria) {
		Assert.notNull(tripCriteria, "tripCriteria");
		long result = 0;
		if (StringUtils.isNotBlank(tripCriteria.getMemberId())) {
			// TODO find by memberId throw user profile service
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isBlank(tripCriteria.getUserId())) {
				// my account
				result = tripRepository.getCountFindForMyAccountByCriteria(tripCriteria);
			} else {
				// not my account
				result = tripRepository.getCountFindForNotMyAccountByCriteria(tripCriteria);
			}
		} else {
			// feed
			result = tripRepository.getCountForFeedByCriteria(tripCriteria);
		}
		return result;
	}

	@Override
	public TableItem getNearTableItem(Trip trip) {
		Assert.notNull(trip, "trip");
		Assert.notEmpty(trip.getTable(), "trip.table");
		Arrays.sort(trip.getTable(), new TableItemComparator());
		return trip.getTable()[0];
	}

	@Override
	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period) {
		Assert.notNull(trip, "trip");
		Assert.notEmpty(trip.getTable(), "trip.table");
		Arrays.sort(trip.getTable(), new TableItemComparator());
		for (TableItem item : trip.getTable()) {
			if (period.getDateBegin() != null && item.getBegin() != null
					&& item.getBegin().before(period.getDateBegin())) {
				continue;
			}
			if (period.getDateEnd() != null && item.getEnd() != null && item.getEnd().after(period.getDateEnd())) {
				continue;
			}
			return item;
		}

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
}
