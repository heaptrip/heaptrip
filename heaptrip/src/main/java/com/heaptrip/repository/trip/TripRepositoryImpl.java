package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.DatabaseCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.trip.helper.QueryHelper;
import com.heaptrip.repository.trip.helper.QueryHelperFactory;
import com.heaptrip.util.collection.IteratorConverter;
import com.heaptrip.util.language.LanguageUtils;
import com.mongodb.WriteResult;

@Service
public class TripRepositoryImpl extends CrudRepositoryImpl<Trip> implements TripRepository {

	private static final Logger logger = LoggerFactory.getLogger(TripRepositoryImpl.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	protected String getCollectionName() {
		return Trip.COLLECTION_NAME;
	}

	@Override
	protected Class<Trip> getCollectionClass() {
		return Trip.class;
	}

	@Override
	public void setDeleted(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {deleted: #, 'status.value': #, allowed : []}}",
				Calendar.getInstance().getTime(), ContentStatusEnum.DELETED);
		logger.debug("WriteResult for set trip deleted: {}", wr);
	}

	@Override
	public List<Trip> findByTripFeedCriteria(TripFeedCriteria criteria) {
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findByTripMyAccountCriteria(TripMyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
			tripIds = memberRepository.findTripIdsByUserId(criteria.getUserId());
		} else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository
					.findIdsByContentTypeAndAccountId(ContentEnum.TRIP, criteria.getUserId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public List<Trip> findByTripForeignAccountCriteria(TripForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
			tripIds = memberRepository.findTripIdsByUserId(criteria.getOwnerId());
		} else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(ContentEnum.TRIP,
					criteria.getOwnerId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory
				.getInstance(QueryHelperFactory.FOREIGN_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	private List<Trip> findByCriteria(DatabaseCriteria criteria, QueryHelper<ContentCriteria> queryHelper,
			Object... objects) {
		MongoCollection coll = getCollection();
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria, objects);
		String projection = queryHelper.getProjection(criteria.getLocale());
		String sort = queryHelper.getSort(criteria.getSort());
		int skip = (criteria.getSkip() != null) ? criteria.getSkip().intValue() : 0;
		int limit = (criteria.getLimit() != null) ? criteria.getLimit().intValue() : 0;
		String hint = queryHelper.getHint(criteria);
		if (logger.isDebugEnabled()) {
			String msg = String
					.format("find trips\n->queryHelper %s\n->query: %s\n->parameters: %s\n->projection: %s\n->sort: %s\n->skip: %d limit: %d\n->hint: %s",
							queryHelper.getClass(), query, ArrayUtils.toString(parameters), projection, sort, skip,
							limit, hint);
			logger.debug(msg);
		}
		Iterable<Trip> iter = coll.find(query, parameters).projection(projection).sort(sort).skip(skip).limit(limit)
				.hint(hint).as(Trip.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public long getCountByTripFeedCriteria(TripFeedCriteria criteria) {
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByTripMyAccountCriteria(TripMyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
			tripIds = memberRepository.findTripIdsByUserId(criteria.getUserId());
		} else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository
					.findIdsByContentTypeAndAccountId(ContentEnum.TRIP, criteria.getUserId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public long getCountByTripForeignAccountCriteria(TripForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
			tripIds = memberRepository.findTripIdsByUserId(criteria.getOwnerId());
		} else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(ContentEnum.TRIP,
					criteria.getOwnerId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory
				.getInstance(QueryHelperFactory.FOREIGN_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	private long getCountByCriteria(ContentCriteria criteria, QueryHelper<ContentCriteria> queryHelper,
			Object... objects) {
		MongoCollection coll = getCollection();
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria, objects);
		if (logger.isDebugEnabled()) {
			String msg = String.format("get trips count\n->queryHelper: %s\n->query: %s\n->parameters: %s",
					queryHelper.getClass(), query, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}
		return coll.count(query, parameters);
	}

	@Override
	public Trip getInfo(String tripId, Locale locale) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String projection = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1,"
						+ " status: 1, 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, 'description.%s': 1, 'description.main': 1,"
						+ " 'table._id': 1, 'table.begin': 1, 'table.end': 1, 'table.min': 1, 'table.max': 1, 'table.status': 1, 'table.users': 1,"
						+ " 'table.price': 1, image: 1, created: 1, owners:1, views: 1, mainLang: 1, rating: 1, comments: 1, langs: 1}",
						lang, lang, lang, lang, lang);
		if (logger.isDebugEnabled()) {
			String msg = String.format("get trip info\n->query: %s\n->parameters: %s\n->projection: %s", query, tripId,
					projection);
			logger.debug(msg);
		}
		return coll.findOne(query, tripId).projection(projection).as(Trip.class);
	}

	@Override
	public void updateInfo(Trip trip, Locale locale) {
		String query = "{_id: #}";

		String updateQuery = null;
		List<Object> parameters = new ArrayList<>();

		String lang = LanguageUtils.getLanguageByLocale(locale);
		String mainLang = trip.getMainLang();

		if (mainLang.equals(lang)) {
			// update main language
			updateQuery = String
					.format("{$addToSet: {langs: #}, $set: {categories: #, allCategories: #, regions: #, allRegions: #, 'name.main': #, 'name.%s': #, "
							+ "'summary.main': #, 'summary.%s': #, 'description.main': #, 'description.%s': #, image: #, table: #}}",
							lang, lang, lang);

			parameters.add(lang);
			parameters.add(trip.getCategories());
			parameters.add(trip.getAllCategories());
			parameters.add(trip.getRegions());
			parameters.add(trip.getAllRegions());
			parameters.add(trip.getName().getValue(locale));
			parameters.add(trip.getName().getValue(locale));
			parameters.add(trip.getSummary().getValue(locale));
			parameters.add(trip.getSummary().getValue(locale));
			parameters.add(trip.getDescription().getValue(locale));
			parameters.add(trip.getDescription().getValue(locale));
			parameters.add(trip.getImage());
			parameters.add(trip.getTable());
		} else {
			updateQuery = String
					.format("{$addToSet: {langs: #}, $set: {categories: #, allCategories: #, regions: #, allRegions: #, 'name.%s': #, 'summary.%s': #, 'description.%s': #, image: #, table: #}}",
							lang, lang, lang);

			parameters.add(lang);
			parameters.add(trip.getCategories());
			parameters.add(trip.getAllCategories());
			parameters.add(trip.getRegions());
			parameters.add(trip.getAllRegions());
			parameters.add(trip.getName().getValue(locale));
			parameters.add(trip.getSummary().getValue(locale));
			parameters.add(trip.getDescription().getValue(locale));
			parameters.add(trip.getImage());
			parameters.add(trip.getTable());
		}

		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"update trip info\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
					query, trip.getId(), updateQuery, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}

		MongoCollection coll = getCollection();
		WriteResult wr = coll.update(query, trip.getId()).with(updateQuery, parameters.toArray());
		logger.debug("WriteResult for update trip info: {}", wr);
	}

	@Override
	public void incTableMembers(String tripId, String tableId, int value) {
		String query = "{_id: #, 'table._id': #}";
		List<Object> parameters = new ArrayList<>();
		parameters.add(tripId);
		parameters.add(tableId);
		String updateQuery = "{$inc: {'table.$.members': #}}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"inc table members\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
					query, ArrayUtils.toString(parameters), updateQuery, value);
			logger.debug(msg);
		}
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update(query, parameters.toArray()).with(updateQuery, value);
		logger.debug("WriteResult for inc table members: {}", wr);
	}

	@Override
	public void setTableStatus(String tripId, String tableId, TableStatus status) {
		MongoCollection coll = getCollection();
		String query = "{_id: #, 'table._id': #}";
		List<Object> parameters = new ArrayList<>();
		parameters.add(tripId);
		parameters.add(tableId);
		String updateQuery = "{$set: {'table.$.status': #}}";
		if (logger.isDebugEnabled()) {
			String msg = String
					.format("update table item status\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
							query, ArrayUtils.toString(parameters), updateQuery, status);
			logger.debug(msg);
		}
		WriteResult wr = coll.update(query, parameters.toArray()).with(updateQuery, status);
		logger.debug("WriteResult for set table status: {}", wr);
	}

	@Override
	public Trip getMainLanguage(String tripId) {
		MongoCollection coll = getCollection();
		return coll.findOne("{_id: #}", tripId).projection("{_class: 1, mainLang: 1}").as(getCollectionClass());
	}

	@Override
	public void removeLanguage(String tripId, Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);

		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = String
				.format("{$pull: {langs: #}, $unset: {'name.%s': 1, 'summary.%s': 1, 'description.%s': 1, 'route.text.%s': 1}}",
						lang, lang, lang, lang);

		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"remove language\n->query: %s\n->parameters: [%s]\n->updateQuery: %s\n->updateParameters: [%s]",
					query, tripId, updateQuery, lang);
			logger.debug(msg);
		}
		WriteResult wr = coll.update(query, tripId).with(updateQuery, lang);
		logger.debug("WriteResult for set table status: {}", wr);
	}

	@Override
	public Trip getRoute(String tripId, Locale locale) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format(
				"{_class: 1, 'route._id': 1, 'route.text.main': 1, 'route.text.%s': 1, 'route.map': 1}", lang);

		if (logger.isDebugEnabled()) {
			String msg = String.format("get trip route\n->query: %s\n->parameters: %s\n->projection: %s", query,
					tripId, fields);
			logger.debug(msg);
		}
		return coll.findOne(query, tripId).projection(fields).as(Trip.class);
	}

	@Override
	public void updateRoute(Trip trip, Locale locale) {
		String query = "{_id: #}";

		String updateQuery = null;
		List<Object> parameters = new ArrayList<>();

		String lang = LanguageUtils.getLanguageByLocale(locale);
		String mainLang = trip.getMainLang();

		if (mainLang.equals(lang)) {
			// update main language
			updateQuery = String.format("{$set: {'route.text.main': #, 'route.text.%s': #, 'route.map': #}}", lang);

			parameters.add(trip.getRoute().getText().getValue(locale));
			parameters.add(trip.getRoute().getText().getValue(locale));
			parameters.add(trip.getRoute().getMap());
		} else {
			updateQuery = String.format("{$set: {'route.text.%s': #, 'route.map': #}}", lang);

			parameters.add(trip.getRoute().getText().getValue(locale));
			parameters.add(trip.getRoute().getMap());
		}

		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"update trip route\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
					query, trip.getId(), updateQuery, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}

		MongoCollection coll = getCollection();
		WriteResult wr = coll.update(query, trip.getId()).with(updateQuery, parameters.toArray());
		logger.debug("WriteResult for update trip route: {}", wr);
	}
}