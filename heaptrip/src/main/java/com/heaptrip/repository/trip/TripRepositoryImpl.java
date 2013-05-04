package com.heaptrip.repository.trip;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.util.LanguageUtils;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service(TripRepository.SERVICE_NAME)
public class TripRepositoryImpl implements TripRepository {

	private static final Logger logger = LoggerFactory.getLogger(TripRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Override
	public void save(Trip trip) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.save(trip);
		logger.debug("WriteResult for save trip: {}", wr);
	}

	@Override
	public void removeTrip(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.remove("{_id: #}", tripId);
		logger.debug("WriteResult for remove trip: {}", wr);
	}

	@Override
	public void setDeleted(String tripId, String ownerId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #, owner._id : #}", tripId, ownerId).with(
				"{$set: {deleted: #, allowed : null}}", Calendar.getInstance().getTime());
		logger.debug("WriteResult for set trip deleted: {}", wr);
	}

	@Override
	public Trip findById(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		return coll.findOne("{ _id : #}", tripId).as(Trip.class);
	}

	@Override
	public List<Trip> findForFeedByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findForMyAccountByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Trip> findForNotMyAccountByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.NOT_MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	private List<Trip> findByCriteria(TripCriteria criteria, QueryHelper queryHelper) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria);
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
	public long getCountForFeedByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountFindForMyAccountByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountFindForNotMyAccountByCriteria(TripCriteria criteria) {
		QueryHelper queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.NOT_MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	public long getCountByCriteria(TripCriteria criteria, QueryHelper queryHelper) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria);
		if (logger.isDebugEnabled()) {
			String msg = String.format("get trips count\n->queryHelper: %s\n->query: %s\n->parameters: %s",
					queryHelper.getClass(), query, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}
		return coll.count(query, parameters);
	}
}