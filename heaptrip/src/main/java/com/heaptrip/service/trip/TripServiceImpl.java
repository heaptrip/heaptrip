package com.heaptrip.service.trip;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.entity.trip.Route;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.TableStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.trip.TripException;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.util.language.LanguageUtils;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RegionService regionService;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ErrorService errorService;

	@Autowired
	private ContentSearchService solrContentService;

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

		Set<String> categoryIds = new HashSet<>();
		if (trip.getCategories() != null) {
			for (SimpleCategory simpleCategory : trip.getCategories()) {
				// set content categories
				Assert.notNull(simpleCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(simpleCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", simpleCategory.getId()));
				simpleCategory.setName(category.getName());
				// set all categories
				categoryIds.add(simpleCategory.getId());
				categoryIds.addAll(categoryService.getParentsByCategoryId(simpleCategory.getId()));
			}
		}
		trip.setCategoryIds(categoryIds.toArray(new String[0]));

		Set<String> regionIds = new HashSet<>();
		if (trip.getRegions() != null) {
			for (SimpleRegion simpleRegion : trip.getRegions()) {
				// set content regions
				Assert.notNull(simpleRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(simpleRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", simpleRegion.getId()));
				simpleRegion.setName(region.getName());
				// set all regions
				regionIds.add(simpleRegion.getId());
				regionIds.addAll(regionService.getParentsByRegionId(simpleRegion.getId()));
			}
		}
		trip.setRegionIds(regionIds.toArray(new String[0]));

		if (trip.getTable() != null) {
			for (TableItem item : trip.getTable()) {
				if (item.getId() == null) {
					item.setId(UUID.randomUUID().toString());
				}
				item.setStatus(new TableStatus(TableStatusEnum.OK));
			}
		}

		// set route
		Route route = new Route();
		route.setId(UUID.randomUUID().toString());
		route.setText(new MultiLangText());
		trip.setRoute(route);

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
		trip.setRating(0d);
		trip.setComments(0L);

		// save to db
		tripRepository.save(trip);

		// save to solr
		solrContentService.saveContent(trip.getId());

		return trip;
	}

	@Override
	public void removeTrip(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		long members = memberRepository.getCountByTripId(tripId);
		if (members > 0) {
			throw errorService.createException(TripException.class, ErrorEnum.REMOVE_TRIP_FAILURE);
		}
		tripRepository.setDeleted(tripId);
		// remove from solr
		solrContentService.removeContent(tripId);
	}

	@Override
	public void hardRemoveTrip(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		tripRepository.remove(tripId);
		// remove from solr
		solrContentService.removeContent(tripId);
	}

	@Override
	public List<Trip> getTripsByTripFeedCriteria(TripFeedCriteria tripFeedCriteria) {
		Assert.notNull(tripFeedCriteria, "tripFeedCriteria must not be null");
		Assert.notNull(tripFeedCriteria.getContentType(), "contentType must not be null");
		return tripRepository.findByTripFeedCriteria(tripFeedCriteria);
	}

	@Override
	public List<Trip> getTripsByTripMyAccountCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
		Assert.notNull(tripMyAccountCriteria, "tripMyAccountCriteria must not be null");
		Assert.notNull(tripMyAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(tripMyAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(tripMyAccountCriteria.getUserId()), "userId must not be null");
		return tripRepository.findByTripMyAccountCriteria(tripMyAccountCriteria);
	}

	@Override
	public List<Trip> getTripsByTripForeignAccountCriteria(TripForeignAccountCriteria tripForeignAccountCriteria) {
		Assert.notNull(tripForeignAccountCriteria, "tripForeignAccountCriteria must not be null");
		Assert.notNull(tripForeignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(tripForeignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(tripForeignAccountCriteria.getOwnerId()), "ownerId must not be null");
		return tripRepository.findByTripForeignAccountCriteria(tripForeignAccountCriteria);
	}

	@Override
	public long getTripsCountByTripFeedCriteria(TripFeedCriteria tripFeedCriteria) {
		Assert.notNull(tripFeedCriteria, "tripFeedCriteria must not be null");
		Assert.notNull(tripFeedCriteria.getContentType(), "contentType must not be null");
		return tripRepository.getCountByTripFeedCriteria(tripFeedCriteria);
	}

	@Override
	public long getTripsCountByTripMyAccountCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
		Assert.notNull(tripMyAccountCriteria, "tripMyAccountCriteria must not be null");
		Assert.notNull(tripMyAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(tripMyAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(tripMyAccountCriteria.getUserId()), "userId must not be null");
		return tripRepository.getCountByTripMyAccountCriteria(tripMyAccountCriteria);
	}

	@Override
	public long getTripsCountByTripForeignAccountCriteria(TripForeignAccountCriteria tripForeignAccountCriteria) {
		Assert.notNull(tripForeignAccountCriteria, "tripForeignAccountCriteria must not be null");
		Assert.notNull(tripForeignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(tripForeignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(StringUtils.isNotBlank(tripForeignAccountCriteria.getOwnerId()), "ownerId must not be null");
		return tripRepository.getCountByTripForeignAccountCriteria(tripForeignAccountCriteria);
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

		Set<String> categoryIds = new HashSet<>();
		if (trip.getCategories() != null) {
			for (SimpleCategory simpleCategory : trip.getCategories()) {
				// set content categories
				Assert.notNull(simpleCategory.getId(), "category.id must not be null");
				Category category = categoryRepository.findOne(simpleCategory.getId());
				Assert.notNull(category, String.format("error category.id: %s", simpleCategory.getId()));
				simpleCategory.setName(category.getName());
				// set all categories
				categoryIds.add(simpleCategory.getId());
				categoryIds.addAll(categoryService.getParentsByCategoryId(simpleCategory.getId()));
			}
		}
		trip.setCategoryIds(categoryIds.toArray(new String[0]));

		Set<String> regionIds = new HashSet<>();
		if (trip.getRegions() != null) {
			for (SimpleRegion simpleRegion : trip.getRegions()) {
				// set content regions
				Assert.notNull(simpleRegion.getId(), "region.id must not be null");
				Region region = regionRepository.findOne(simpleRegion.getId());
				Assert.notNull(region, String.format("error region.id: %s", simpleRegion.getId()));
				simpleRegion.setName(region.getName());
				// set all regions
				regionIds.add(simpleRegion.getId());
				regionIds.addAll(regionService.getParentsByRegionId(simpleRegion.getId()));
			}
		}
		trip.setRegionIds(regionIds.toArray(new String[0]));

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

		// update to db
		tripRepository.updateInfo(trip, locale);

		// save to solr
		solrContentService.saveContent(trip.getId());
	}

	@Override
	public void removeTripLocale(String tripId, Locale locale) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(locale, "locale must not be null");
		String removeLang = LanguageUtils.getLanguageByLocale(locale);
		Trip trip = tripRepository.getMainLanguage(tripId);
		if (trip == null) {
			throw new IllegalArgumentException(String.format("trip with id=%s is not found", tripId));
		}
		String mainLang = trip.getMainLang();
		if (mainLang == null) {
			throw new IllegalStateException(String.format("trip with id=%s does not have a main language", tripId));
		}
		if (mainLang.equals(removeLang)) {
			throw errorService.createException(TripException.class, ErrorEnum.REMOVE_TRIP_LANGUAGE_FAILURE);
		}
		tripRepository.removeLanguage(tripId, locale);

		// save to solr
		solrContentService.saveContent(tripId);
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
