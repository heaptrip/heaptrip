package com.heaptrip.repository.content;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.DatabaseCriteria;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.content.helper.QueryHelper;
import com.heaptrip.repository.content.helper.QueryHelperFactory;
import com.heaptrip.util.LanguageUtils;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class ContentRepositoryImpl extends CrudRepositoryImpl<Content> implements ContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	protected String getCollectionName() {
		return Content.COLLECTION_NAME;
	}

	@Override
	protected Class<Content> getCollectionClass() {
		return Content.class;
	}

	@Override
	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {'status.value': #, allowed: #}}", status,
				allowed);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void incViews(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$inc: {views: 1}}");
		logger.debug("WriteResult for inc views: {}", wr);
	}

	@Override
	public List<Content> findByIds(Collection<String> ids, Locale locale) {
		MongoCollection coll = getCollection();
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1, status: 1,"
						+ " 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, created: 1, owners: 1, views: 1,"
						+ " mainLang: 1, rating: 1, comments: 1}", lang, lang, lang, lang);
		Iterable<Content> iter = coll.find("{_id: {$in: #}}", ids).projection(fields).as(Content.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public List<Content> findByFeedCriteria(FeedCriteria criteria) {
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Content> findByMyAccountCriteria(MyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP, criteria.getUserId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public List<Content> findByForeignAccountCriteria(ForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP, criteria.getOwnerId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory
				.getInstance(QueryHelperFactory.FOREIGN_ACCOUNT_HELPER);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	private List<Content> findByCriteria(DatabaseCriteria criteria, QueryHelper<ContentCriteria> queryHelper,
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
		Iterable<Content> iter = coll.find(query, parameters).projection(projection).sort(sort).skip(skip).limit(limit)
				.hint(hint).as(Content.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public long getCountByFeedCriteria(FeedCriteria criteria) {
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMyAccountCriteria(MyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP, criteria.getUserId());
		}
		QueryHelper<ContentCriteria> queryHelper = QueryHelperFactory.getInstance(QueryHelperFactory.MY_ACCOUNT_HELPER);
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public long getCountByForeignAccountCriteria(ForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findContentIdsByTypeAndUserId(ContentEnum.TRIP, criteria.getOwnerId());
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
}
