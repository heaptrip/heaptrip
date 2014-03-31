package com.heaptrip.repository.helper;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.service.criteria.Criteria;
import com.heaptrip.util.collection.IteratorConverter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jongo.Find;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractQueryHelper<T extends Criteria, M extends BaseObject> implements QueryHelper<T, M> {

    private static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);

    @Autowired
    protected MongoContext mongoContext;

    protected abstract Class<M> getCollectionClass();

    protected abstract String getCollectionName();

    @Override
    public List<M> findByCriteria(T criteria, Object... arguments) {
        String query = getQuery(criteria);
        Object[] parameters = getParameters(criteria, arguments);
        String projection = getProjection(criteria);
        String sort = getSort(criteria);
        String hint = getHint(criteria);

        if (logger.isDebugEnabled()) {
            String msg = String
                    .format("findByCriteria\n" +
                            "->queryHelper %s\n" +
                            "->query: %s\n" +
                            "->parameters: %s\n" +
                            "->projection: %s\n" +
                            "->sort: %s\n" +
                            "->skip: %d\n" +
                            "->limit: %d\n" +
                            "->hint: %s",
                            this.getClass(),
                            query,
                            ArrayUtils.toString(parameters),
                            projection,
                            sort,
                            (criteria.getSkip() == null) ? null : criteria.getSkip().intValue(),
                            (criteria.getLimit() == null) ? null : criteria.getLimit().intValue(),
                            hint);
            logger.debug(msg);
        }

        MongoCollection mongoCollection = mongoContext.getCollection(getCollectionName());
        Find find;

        if (!StringUtils.isEmpty(query) && ArrayUtils.isNotEmpty(parameters)) {
            find = mongoCollection.find(query, parameters);
        } else if (!StringUtils.isEmpty(query)) {
            find = mongoCollection.find(query);
        } else {
            find = mongoCollection.find();
        }

        if (!StringUtils.isEmpty(projection)) {
            find = find.projection(projection);
        }

        if (!StringUtils.isEmpty(sort)) {
            find = find.sort(sort);
        }

        if (criteria.getSkip() != null) {
            find = find.skip(criteria.getSkip().intValue());
        }

        if (criteria.getLimit() != null) {
            find = find.limit(criteria.getLimit().intValue());
        }

        if (!StringUtils.isEmpty(hint)) {
            find = find.hint(hint);
        }

        return IteratorConverter.copyIterator(find.as(getCollectionClass()).iterator());
    }

    @Override
    public long countByCriteria(T criteria, Object... arguments) {
        String query = getQuery(criteria);
        Object[] parameters = getParameters(criteria, arguments);

        if (logger.isDebugEnabled()) {
            String msg = String
                    .format("countByCriteria\n" +
                            "->queryHelper %s\n" +
                            "->query: %s\n" +
                            "->parameters: %s",
                            this.getClass(),
                            query,
                            ArrayUtils.toString(parameters));
            logger.debug(msg);
        }

        MongoCollection mongoCollection = mongoContext.getCollection(getCollectionName());
        return mongoCollection.count(query, parameters);
    }
}
