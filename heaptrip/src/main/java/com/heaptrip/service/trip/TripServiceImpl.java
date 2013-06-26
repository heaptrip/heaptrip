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

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentRegion;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.TableStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.adm.ErrorService;
import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;
import com.heaptrip.domain.service.trip.MyAccountTripCriteria;
import com.heaptrip.domain.service.trip.SearchPeriod;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.util.LanguageUtils;

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
	private ErrorService errorService;

	@Override
	public Trip saveTrip(Trip trip, Locale locale) {
		Assert.notNull(trip, "trip must not be null");
		Assert.notNull(trip, "locale must not be null");
		Assert.notNull(trip.getOwner(), "owner must not be null");
		Assert.notNull(trip.getOwner().getId(), "owner.id must not be null");
		Assert.notEmpty(trip.getName(), "name must not be empty");
		Assert.notEmpty(trip.getSummary(), "summary must not be empty");
		Assert.notEmpty(trip.getDescription(), "description must not be empty");

		// TODO read and set owner name, account type and rating
		trip.getOwner().setName("Ivan Petrov");
		trip.getOwner().setRating(0D);
		trip.getOwner().setType(AccountEnum.USER);

		// TODO if owner account type == (CLUB or COMPANY) then set owners
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
				item.setStatus(new TableStatus(TableStatusEnum.OK));
			}
		}

		// set lang
		String lang = LanguageUtils.getLanguageByLocale(locale);
		trip.setLangs(new String[] { lang });
		trip.setMainLang(lang);
		trip.getName().setMainLanguage(lang);
		trip.getSummary().setMainLanguage(lang);
		trip.getDescription().setMainLanguage(lang);

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
	public List<Trip> getTripsByFeedTripCriteria(FeedTripCriteria feedTripCriteria) {
		Assert.notNull(feedTripCriteria, "feedTripCriteria must not be null");
		Assert.notNull(feedTripCriteria.getContentType(), "contentType must not be null");
		return tripRepository.findByFeedTripCriteria(feedTripCriteria);
	}

	@Override
	public List<Trip> getTripsByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria) {
		Assert.notNull(myAccountTripCriteria, "myAccountTripCriteria must not be null");
		Assert.notNull(myAccountTripCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountTripCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(myAccountTripCriteria.getUserId()), "userId must not be null");
		return tripRepository.findByMyAccountTripCriteria(myAccountTripCriteria);
	}

	@Override
	public List<Trip> getTripsByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria) {
		Assert.notNull(foreignAccountTripCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountTripCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountTripCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountTripCriteria.getOwnerId()), "ownerId must not be null");
		return tripRepository.findByForeignAccountTripCriteria(foreignAccountTripCriteria);
	}

	@Override
	public long getTripsCountByFeedTripCriteria(FeedTripCriteria feedTripCriteria) {
		Assert.notNull(feedTripCriteria, "feedTripCriteria must not be null");
		Assert.notNull(feedTripCriteria.getContentType(), "contentType must not be null");
		return tripRepository.getCountByFeedTripCriteria(feedTripCriteria);
	}

	@Override
	public long getTripsCountByMyAccountTripCriteria(MyAccountTripCriteria myAccountTripCriteria) {
		Assert.notNull(myAccountTripCriteria, "myAccountTripCriteria must not be null");
		Assert.notNull(myAccountTripCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountTripCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(myAccountTripCriteria.getUserId()), "userId must not be null");
		return tripRepository.getCountByMyAccountTripCriteria(myAccountTripCriteria);
	}

	@Override
	public long getTripsCountByForeignAccountTripCriteria(ForeignAccountTripCriteria foreignAccountTripCriteria) {
		Assert.notNull(foreignAccountTripCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountTripCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountTripCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountTripCriteria.getOwnerId()), "ownerId must not be null");
		return tripRepository.getCountByForeignAccountTripCriteria(foreignAccountTripCriteria);
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
		Assert.notNull(locale, "locale must not be null");
		Assert.notNull(trip.getId(), "trip.id must not be null");
		Assert.notNull(trip.getMainLang(), "mainLang must not be empty");
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
					item.setStatus(new TableStatus(TableStatusEnum.OK));
				}
			}
		}

		// set lang
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String mainLang = trip.getMainLang();
		if (mainLang.equals(lang)) {
			trip.getName().setMainLanguage(mainLang);
			trip.getSummary().setMainLanguage(mainLang);
			trip.getDescription().setMainLanguage(mainLang);
		}

		tripRepository.update(trip, locale);
	}

	@Override
	public void removeTripLocale(String tripId, Locale locale) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(locale, "locale must not be null");
		String removeLang = LanguageUtils.getLanguageByLocale(locale);
		String mainLang = tripRepository.getMainLanguage(tripId);
		if (mainLang != null && mainLang.equals(removeLang)) {
			throw errorService.createBusinessExeption(ErrorEnum.REMOVE_TRIP_LANGUAGE_FAILURE);
		}
		tripRepository.removeLanguage(tripId, locale);
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
}
