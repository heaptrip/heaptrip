package com.heaptrip.repository.content;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.ContentSortCriteria;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class ContentRepositoryImpl extends CrudRepositoryImpl<Content> implements ContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Autowired
	private QueryHelperFactory queryHelperFactory;

	@Override
	protected String getCollectionName() {
		return CollectionEnum.CONTENTS.getName();
	}

	@Override
	protected Class<Content> getCollectionClass() {
		return Content.class;
	}

	@Override
	public void setDeleted(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {deleted: #, 'status.value': #, allowed : []}}",
				Calendar.getInstance().getTime(), ContentStatusEnum.DELETED);
		logger.debug("WriteResult for set content deleted: {}", wr);
	}

	@Override
	public String getOwnerId(String contentId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, 'owner._id': 1}")
				.as(getCollectionClass());
		if (content == null || content.getOwner() == null) {
			return null;
		} else {
			return content.getOwner().getId();
		}
	}

	@Override
	public boolean isOwner(String contentId, String userId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #, $or: [{'owner._id': #}, {owners: #}]}", contentId, userId, userId)
				.projection("{_class: 1}").as(getCollectionClass());
		return (content == null) ? false : true;
	}

	@Override
	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {'status.value': #, allowed: #}}", status,
				allowed);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void incViews(String contentId, String userIdOrRemoteIp) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #, 'views.ids': {$not: {$in: #}}}", contentId,
				Arrays.asList(userIdOrRemoteIp)).with("{$push:{'views.ids': #}, $inc: {'views.count': 1}}",
				userIdOrRemoteIp);
		logger.debug("WriteResult for inc views: {}", wr);
	}

	@Override
	public List<Content> findByIds(Collection<String> ids, Locale locale) {
		FeedCriteria criteria = new FeedCriteria();
		criteria.setLocale(locale);
		MongoCollection coll = getCollection();
		String fields = queryHelperFactory.getHelperByCriteria(FeedCriteria.class).getProjection(criteria);
		Iterable<Content> iter = coll.find("{_id: {$in: #}}", ids).projection(fields).as(Content.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public List<Content> findByFeedCriteria(FeedCriteria criteria) {
		QueryHelper<FeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(FeedCriteria.class);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Content> findByMyAccountCriteria(MyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public List<Content> findByForeignAccountCriteria(ForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, tripIds);
	}

	private <T extends ContentSortCriteria> List<Content> findByCriteria(T criteria, QueryHelper<T> queryHelper,
			Object... objects) {
		MongoCollection coll = getCollection();
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria, objects);
		String projection = queryHelper.getProjection(criteria);
		String sort = queryHelper.getSort(criteria);
		int skip = (criteria.getSkip() != null) ? criteria.getSkip().intValue() : 0;
		int limit = (criteria.getLimit() != null) ? criteria.getLimit().intValue() : 0;
		String hint = queryHelper.getHint(criteria);
		if (logger.isDebugEnabled()) {
			String msg = String
					.format("find contents\n->queryHelper %s\n->query: %s\n->parameters: %s\n->projection: %s\n->sort: %s\n->skip: %d limit: %d\n->hint: %s",
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
		QueryHelper<FeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(FeedCriteria.class);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMyAccountCriteria(MyAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	@Override
	public long getCountByForeignAccountCriteria(ForeignAccountCriteria criteria) {
		List<String> tripIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, tripIds);
	}

	private <T extends ContentCriteria> long getCountByCriteria(T criteria, QueryHelper<T> queryHelper,
			Object... objects) {
		MongoCollection coll = getCollection();
		String query = queryHelper.getQuery(criteria);
		Object[] parameters = queryHelper.getParameters(criteria, objects);
		if (logger.isDebugEnabled()) {
			String msg = String.format("get contents count\n->queryHelper: %s\n->query: %s\n->parameters: %s",
					queryHelper.getClass(), query, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}
		return coll.count(query, parameters);
	}

	@Override
	public Date getDateCreated(String contentId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, created: 1}")
				.as(getCollectionClass());
		return (content == null) ? null : content.getCreated();
	}

	@Override
	public ContentEnum getContentTypeByContentId(String contentId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1}").as(getCollectionClass());
		for (ContentEnum contentType : ContentEnum.values()) {
			if (contentType.getClazz().equals(content.getClass().getName())) {
				return contentType;
			}
		}
		return null;
	}

	@Override
	public ContentRating getRating(String contentId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, rating: 1}")
				.as(getCollectionClass());
		return (content == null) ? null : content.getRating();
	}

	@Override
	public void updateRating(String contentId, double ratingValue) {
		MongoCollection coll = getCollection();
		coll.update("{_id: #}", contentId).with("{$set: {rating.value: #}, $inc: {'rating.count': 1}}", ratingValue);
	}
}
