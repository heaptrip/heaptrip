package com.heaptrip.service.trip;

import java.io.IOException;
import java.io.InputStream;
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
import com.heaptrip.domain.entity.ContentRegion;
import com.heaptrip.domain.entity.ContentStatus;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.Image;
import com.heaptrip.domain.entity.ImageEnum;
import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.TableStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.RegionRepository;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.ImageStorageService;
import com.heaptrip.domain.service.SearchPeriod;
import com.heaptrip.domain.service.adm.ErrorService;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ImageStorageService imageStorageService;

	@Autowired
	private ErrorService errorService;

	@Override
	public Trip saveTrip(Trip trip) {
		Assert.notNull(trip, "trip must not be null");
		Assert.notNull(trip.getOwner(), "owner must not be null");
		Assert.notNull(trip.getOwner().getId(), "owner.id must not be null");
		Assert.notEmpty(trip.getName(), "name must not be empty");
		Assert.notEmpty(trip.getLangs(), "langs must not be empty");
		Assert.notEmpty(trip.getSummary(), "summary must not be empty");
		Assert.notEmpty(trip.getDescription(), "description must not be empty");

		// TODO
		// read and set owner name, account type and rating
		// if owner account type == (CLUB or COMPANY) then set owners
		trip.setOwners(new String[] { trip.getOwner().getId() });

		if (trip.getCategories() != null) {
			for (ContentCategory contentCategory : trip.getCategories()) {
				Assert.notNull(contentCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(contentCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", contentCategory.getId()));
				contentCategory.setName(category.getName());
			}
		}

		if (trip.getRegions() != null) {
			for (ContentRegion contentRegion : trip.getRegions()) {
				Assert.notNull(contentRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(contentRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", contentRegion.getId()));
				contentRegion.setName(region.getName());
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

		trip.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
		trip.setCreated(new Date());
		trip.setDeleted(null);
		trip.setViews(0L);
		trip.setRating(0d);
		trip.setComments(0L);

		return tripRepository.save(trip);
	}

	@Override
	public void removeTrip(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		long members = memberRepository.getCountByTripId(tripId);
		if (members > 0) {
			throw errorService.createBusinessExeption(ErrorEnum.REMOVE_TRIP_FAILURE);
		}
		tripRepository.setDeleted(tripId);
	}

	@Override
	public void hardRemoveTrip(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		tripRepository.remove(tripId);
	}

	@Override
	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria) {
		Assert.notNull(tripCriteria, "tripCriteria must not be null");
		List<Trip> result = null;
		if (StringUtils.isNotBlank(tripCriteria.getMemberId())) {
			// member
			result = tripRepository.findByMemberCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getFavoriteUserId())) {
			// favorites
			result = tripRepository.findByFavoritesCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isNotBlank(tripCriteria.getUserId())
					&& tripCriteria.getOwnerId().equals(tripCriteria.getUserId())) {
				// my account
				result = tripRepository.findByMyAccountCriteria(tripCriteria);
			} else {
				// not my account
				result = tripRepository.findByNotMyAccountCriteria(tripCriteria);
			}
		} else {
			// feed
			result = tripRepository.findByFeedCriteria(tripCriteria);
		}
		return result;
	}

	@Override
	public long getTripsCountByCriteria(TripCriteria tripCriteria) {
		Assert.notNull(tripCriteria, "tripCriteria must not be null");
		long result = 0;
		if (StringUtils.isNotBlank(tripCriteria.getMemberId())) {
			// member
			result = tripRepository.getCountByMemberCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getFavoriteUserId())) {
			// favorites
			result = tripRepository.getCountByFavoritesCriteria(tripCriteria);
		} else if (StringUtils.isNotBlank(tripCriteria.getOwnerId())) {
			if (StringUtils.isNotBlank(tripCriteria.getUserId())
					&& tripCriteria.getOwnerId().equals(tripCriteria.getUserId())) {
				// my account
				result = tripRepository.getCountByMyAccountCriteria(tripCriteria);
			} else {
				// not my account
				result = tripRepository.getCountByNotMyAccountCriteria(tripCriteria);
			}
		} else {
			// feed
			result = tripRepository.getCountByFeedCriteria(tripCriteria);
		}
		return result;
	}

	@Override
	public TableItem getNearTableItem(Trip trip) {
		Assert.notNull(trip, "trip must not be null");
		Assert.notEmpty(trip.getTable(), "trip.table must not be empty");
		Arrays.sort(trip.getTable(), new TableItemComparator());
		return trip.getTable()[0];
	}

	@Override
	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period) {
		Assert.notNull(trip, "trip must not be null");
		Assert.notEmpty(trip.getTable(), "trip.table must not be empty");
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
	public Trip getTripInfo(String tripId, Locale locale) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(locale, "locale must not be null");
		return tripRepository.getInfo(tripId, locale);
	}

	@Override
	public void updateTripInfo(Trip trip, Locale locale) {
		Assert.notNull(trip, "trip must not be null");
		Assert.notNull(trip.getId(), "trip.id must not be null");
		Assert.notEmpty(trip.getName(), "name must not be empty");
		Assert.notEmpty(trip.getSummary(), "summary must not be empty");
		Assert.notEmpty(trip.getDescription(), "description must not be empty");
		Assert.notEmpty(trip.getLangs(), "langs must not be empty");
		if (trip.getCategories() != null) {
			for (ContentCategory contentCategory : trip.getCategories()) {
				Assert.notNull(contentCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(contentCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", contentCategory.getId()));
				contentCategory.setName(category.getName());
			}
		}
		if (trip.getRegions() != null) {
			for (ContentRegion contentRegion : trip.getRegions()) {
				Assert.notNull(contentRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(contentRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", contentRegion.getId()));
				contentRegion.setName(region.getName());
			}
		}
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
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		TableStatus status = new TableStatus();
		status.setValue(TableStatusEnum.ABORTED);
		status.setText(cause);
		tripRepository.setTableStatus(tripId, tableId, status);
	}

	@Override
	public void cancelTableItem(String tripId, String tableId, String cause) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(tableId, "tableId must not be null");
		TableStatus status = new TableStatus();
		status.setValue(TableStatusEnum.CANCELED);
		status.setText(cause);
		tripRepository.setTableStatus(tripId, tableId, status);
	}

	@Override
	public Image saveImage(String fileName, InputStream is) throws IOException {
		Image image = new Image();
		String imageId = imageStorageService.saveImage(fileName, ImageEnum.TRIP_IMAGE, is);
		image.setId(imageId);
		image.setName(fileName);
		image.setUploaded(new Date());
		return image;
	}
}
