package com.heaptrip.service.trip;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.entity.ContentStatus;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.TableStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.trip.MemberRepository;
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

	@Autowired
	private MemberRepository memberRepository;

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

		if (trip.getTable() != null) {
			for (TableItem item : trip.getTable()) {
				if (item.getId() == null) {
					item.setId(UUID.randomUUID().toString());
				}
				item.setStatus(new TableStatus());
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
		// TODO add a check that in travel has no members
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
			// find by memberId
			result = tripRepository.findForMemberByCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isBlank(tripCriteria.getUserId())) {
				// my account
				result = tripRepository.findForMyAccountByCriteria(tripCriteria);
			} else {
				// not my account
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
			// find by memberId
			result = tripRepository.getCountForMemberByCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isBlank(tripCriteria.getUserId())) {
				// my account
				result = tripRepository.getCountForMyAccountByCriteria(tripCriteria);
			} else {
				// not my account
				result = tripRepository.getCountForNotMyAccountByCriteria(tripCriteria);
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
			return item;
		}
		return null;
	}

	@Override
	public void setTripStatus(String tripId, String ownerId, ContentStatusEnum status) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(ownerId, "ownerId");
		Assert.notNull(status, "status");
		String[] allowed = null;
		switch (status) {
		case PUBLISHED_ALL:
			allowed = new String[] { "0" };
			break;
		case PUBLISHED_FRIENDS:
			// TODO add owner freinds
			allowed = new String[] { "0" };
			break;
		default:
			break;
		}
		tripRepository.setStatus(tripId, status, allowed);
	}

	@Override
	public void incTripViews(String tripId) {
		Assert.notNull(tripId, "tripId");
		tripRepository.incViews(tripId);
	}

	@Override
	public Trip getTripInfo(String tripId, Locale locale) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(locale, "locale");
		Assert.notNull(locale.getCountry(), "locale.country");
		return tripRepository.getInfo(tripId, locale);
	}

	@Override
	public void updateTripInfo(Trip trip, Locale locale) {
		Assert.notNull(trip, "trip");
		Assert.notNull(trip.getId(), "trip.id");
		Assert.notEmpty(trip.getName(), "name");
		Assert.notEmpty(trip.getSummary(), "summary");
		Assert.notEmpty(trip.getDescription(), "description");
		Assert.notEmpty(trip.getLangs(), "langs");
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
		if (trip.getTable() != null) {
			for (TableItem item : trip.getTable()) {
				if (item.getId() == null) {
					item.setId(UUID.randomUUID().toString());
				}
				if (item.getStatus() == null) {
					item.setStatus(new TableStatus());
				}
			}
		}
		tripRepository.update(trip, locale);
	}

	@Override
	public void abortTableItem(String tripId, String tableId, String cause) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		TableStatus status = new TableStatus();
		status.setValue(TableStatusEnum.ABORTED);
		status.setText(cause);
		tripRepository.setTableStatus(tripId, tableId, status);
	}

	@Override
	public void cancelTableItem(String tripId, String tableId, String cause) {
		Assert.notNull(tripId, "tripId");
		Assert.notNull(tableId, "tableId");
		TableStatus status = new TableStatus();
		status.setValue(TableStatusEnum.CANCELED);
		status.setText(cause);
		tripRepository.setTableStatus(tripId, tableId, status);
	}
}
