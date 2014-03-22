package com.heaptrip.repository.content;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.ContentSortCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.util.collection.IteratorConverter;
import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class FeedRepositoryImpl<M extends BaseObject> extends CrudRepositoryImpl<M> {

    private static final Logger logger = LoggerFactory.getLogger(FeedRepositoryImpl.class);

    protected <T extends ContentSortCriteria> List<M> findByCriteria(T criteria, QueryHelper<T> queryHelper,
                                                                     Object... arguments) {
        String query = queryHelper.getQuery(criteria);
        Object[] parameters = queryHelper.getParameters(criteria, arguments);
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
        MongoCollection coll = getCollection();
        Iterable<M> iter = coll.find(query, parameters).projection(projection).sort(sort).skip(skip).limit(limit)
                .hint(hint).as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
    }

    protected <T extends ContentCriteria> long getCountByCriteria(T criteria, QueryHelper<T> queryHelper,
                                                                  Object... arguments) {
        String query = queryHelper.getQuery(criteria);
        Object[] parameters = queryHelper.getParameters(criteria, arguments);
        if (logger.isDebugEnabled()) {
            String msg = String.format("get contents count\n->queryHelper: %s\n->query: %s\n->parameters: %s",
                    queryHelper.getClass(), query, ArrayUtils.toString(parameters));
            logger.debug(msg);
        }
        MongoCollection coll = getCollection();
        return coll.count(query, parameters);
    }
}
