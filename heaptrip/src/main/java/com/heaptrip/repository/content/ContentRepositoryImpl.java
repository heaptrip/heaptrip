package com.heaptrip.repository.content;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, ownerId: 1}")
                .as(getCollectionClass());
        if (content == null || content.getOwnerId() == null) {
            return null;
        } else {
            return content.getOwnerId();
        }
    }

    @Override
    public boolean isOwner(String contentId, String userId) {
        MongoCollection coll = getCollection();
        Content content = coll.findOne("{_id: #, ownerId: #}", contentId, userId)
                .projection("{_class: 1}").as(getCollectionClass());
        return content != null;
    }

    @Override
    public ContentStatus getStatus(String contentId) {
        MongoCollection coll = getCollection();
        Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, status: 1}")
                .as(getCollectionClass());
        return (content == null) ? null : content.getStatus();
    }

    @Override
    public void setStatus(String tripId, ContentStatus status, String[] allowed) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {status: #, allowed: #}}", status, allowed);
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
        String fields = queryHelperFactory.getHelperByCriteriaClass(FeedCriteria.class).getProjection(criteria);
        Iterable<Content> data = coll.find("{_id: {$in: #}}", ids).projection(fields).as(Content.class);
        return IteratorConverter.copyIterator(data.iterator());
    }

    @Override
    public List<Content> findByFeedCriteria(FeedCriteria criteria) {
        QueryHelper<FeedCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria);
    }

    @Override
    public List<Content> findByMyAccountCriteria(MyAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
                    criteria.getUserId());
        }
        QueryHelper<MyAccountCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria, tripIds);
    }

    @Override
    public List<Content> findByForeignAccountCriteria(ForeignAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
                    criteria.getAccountId());
        }
        QueryHelper<ForeignAccountCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria, tripIds);
    }

    @Override
    public long getCountByFeedCriteria(FeedCriteria criteria) {
        QueryHelper<FeedCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.countByCriteria(criteria);
    }

    @Override
    public long getCountByMyAccountCriteria(MyAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
                    criteria.getUserId());
        }
        QueryHelper<MyAccountCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.countByCriteria(criteria, tripIds);
    }

    @Override
    public long getCountByForeignAccountCriteria(ForeignAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
                    criteria.getAccountId());
        }
        QueryHelper<ForeignAccountCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.countByCriteria(criteria, tripIds);
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

    @Override
    public void addAllowed(String ownerId, String userId) {
        MongoCollection coll = getCollection();
        String query = "{'ownerId': #}";
        String updateQuery = "{$addToSet :{allowed: #}}";
        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "add allowed\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
                    ownerId, updateQuery, userId);
            logger.debug(msg);
        }
        WriteResult wr = coll.update(query, ownerId).multi().with(updateQuery, userId);
        logger.debug("WriteResult for add allowed: {}", wr);
    }

    @Override
    public void removeAllowed(String ownerId, String userId) {
        MongoCollection coll = getCollection();
        String query = "{'ownerId': #}";
        String updateQuery = "{$pull :{allowed: #}}";
        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "remove allowed\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
                    ownerId, updateQuery, userId);
            logger.debug(msg);
        }
        WriteResult wr = coll.update(query, ownerId).multi().with(updateQuery, userId);
        logger.debug("WriteResult for remove allowed: {}", wr);
    }

    @Override
    public long getCountByOwnerIdAndAllowed(String ownerId, String allowedUserId) {
        MongoCollection coll = getCollection();
        return coll.count("{'ownerId': #, allowed: #}", ownerId, allowedUserId);
    }

    @Override
    public String getMainLanguage(String contentId) {
        MongoCollection coll = getCollection();
        Content content = coll.findOne("{_id: #}", contentId).projection("{_class: 1, mainLang: 1}")
                .as(getCollectionClass());
        return (contentId == null) ? null : content.getMainLang();
    }

    @Override
    public boolean haveActiveContent(String ownerId, List<String> statuses) {
        MongoCollection coll = getCollection();
        return coll.count("{ownerId: #, status.value: {$in: #}}", ownerId, statuses) > 0;
    }
}
