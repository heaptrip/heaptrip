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

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.TableStatus;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.FavoriteContentRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.repository.trip.helper.QueryHelper;
import com.heaptrip.repository.trip.helper.QueryHelperFactory;
import com.heaptrip.util.LanguageUtils;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class TripRepositoryImpl implements TripRepository {

	private static final Logger logger = LoggerFactory.getLogger(TripRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	public void save(Trip trip) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.save(trip);
		logger.debug("WriteResult for save trip: {}", wr);
	}

	@Override
	public void removeById(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.remove("{_id: #}", tripId);
		logger.debug("WriteResult for remove trip: {}", wr);
	}

	@Override
	public void setDeleted(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {deleted: #, 'status.value': #, allowed : []}}",
				Calendar.getInstance().getTime(), ContentStatusEnum.DELETED);
		logger.debug("WriteResult for set trip deleted: {}", wr);
	}

	@Override
	public Trip findById(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		return coll.findOne("{ _id: #}", tripId).as(Trip.class);
	}

	@Override
	public List<Trip> findByFeedCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findByMyAccountCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findByNotMyAccountCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.NOT_MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findByMemberCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MEMBER_HELPER);
		List<String> tripIds = memberRepository.findTripIdsByUserId(criteria.getMemberId());
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public List<Trip> findByFavoritesCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FAVORITES_HELPER);
		List<String> tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP,
				criteria.getFavoriteUserId());
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	private List<Trip> findByCriteria(TripCriteria criteria, QueryHelper queryHelper, Object... objects) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria, objects);
		String projection = queryHelper.getProjection(LanguageUtils.getLanguageByLocale(criteria.getLocale()));
		String sort = queryHelper.getSort(criteria.getSort());
		int skip = (criteria.getSkip() != null) ? criteria.getSkip().intValue() : 0;
		int limit = (criteria.getLimit() != null) ? criteria.getLimit().intValue() : 0;
		String hint = queryHelper.getHint(criteria.getSort());
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
	public long getCountByFeedCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMyAccountCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByNotMyAccountCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.NOT_MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMemberCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MEMBER_HELPER);
		List<String> tripIds = memberRepository.findTripIdsByUserId(criteria.getMemberId());
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public long getCountByFavoritesCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FAVORITES_HELPER);
		List<String> tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP,
				criteria.getFavoriteUserId());
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	private long getCountByCriteria(TripCriteria criteria, QueryHelper queryHelper, Object... objects) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
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
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = "{_id: #}";
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String projection = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1,"
						+ " status: 1, 'name.%s': 1, 'summary.%s': 1, 'description.%s': 1, 'table._id': 1, 'table.begin': 1, 'table.end': 1,"
						+ " 'table.min': 1, 'table.max': 1, 'table.status': 1, 'table.users': 1, 'table.price': 1, image: 1,"
						+ " created: 1, owners:1, views: 1, rating: 1, comments: 1, langs: 1}", lang, lang, lang, lang,
						lang);
		if (logger.isDebugEnabled()) {
			String msg = String.format("get trip info\n->query: %s\n->parameters: %s\n->projection: %s", query, tripId,
					projection);
			logger.debug(msg);
		}
		return coll.findOne(query, tripId).projection(projection).as(Trip.class);
	}

	@Override
	public void update(Trip trip, Locale locale) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		// update common data
		String query = "{_id: #}";
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String updateQuery = String
				.format("{$addToSet: {langs: #}, $set: {categories: #, regions: #, 'name.%s': #, 'summary.%s': #, 'description.%s': #, image: #}}",
						lang, lang, lang);
		List<Object> parameters = new ArrayList<>();
		parameters.add(lang);
		parameters.add(trip.getCategories());
		parameters.add(trip.getRegions());
		parameters.add(trip.getName().getValue(locale));
		parameters.add(trip.getSummary().getValue(locale));
		parameters.add(trip.getDescription().getValue(locale));
		parameters.add(trip.getImage());
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"update trip info\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
					query, trip.getId(), updateQuery, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}
		WriteResult wr = coll.update(query, trip.getId()).with(updateQuery, parameters.toArray());
		logger.debug("WriteResult for update trip info: {}", wr);
		// update table items
		if (trip.getTable() != null) {
			for (TableItem item : trip.getTable()) {
				query = "{_id: #, 'table:_id': #}";
				parameters = new ArrayList<>();
				parameters.add(trip.getId());
				parameters.add(item.getId());
				updateQuery = "{$set: {'table.$.begin': #, 'table.$.end': #, 'table.$.min': #, 'table.$.max': #, 'table.$.price': #, 'table.$.status': #}}";
				List<Object> updateParameters = new ArrayList<>();
				updateParameters.add(item.getBegin());
				updateParameters.add(item.getEnd());
				updateParameters.add(item.getMin());
				updateParameters.add(item.getMax());
				updateParameters.add(item.getPrice());
				updateParameters.add(item.getStatus());
				if (logger.isDebugEnabled()) {
					String msg = String
							.format("update table item\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
									query, ArrayUtils.toString(parameters), updateQuery,
									ArrayUtils.toString(updateParameters));
					logger.debug(msg);
				}
				wr = coll.update(query, parameters.toArray()).with(updateQuery, updateParameters.toArray());
				logger.debug("WriteResult for update table item: {}", wr);
			}
		}
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
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update(query, parameters.toArray()).with(updateQuery, value);
		logger.debug("WriteResult for inc table members: {}", wr);
	}

	@Override
	public void setTableStatus(String tripId, String tableId, TableStatus status) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
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
}