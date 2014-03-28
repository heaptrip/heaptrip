package com.heaptrip.repository.image;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.repository.image.ImageRepository;
import com.heaptrip.domain.service.image.criteria.ImageCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageRepositoryImpl extends CrudRepositoryImpl<Image> implements ImageRepository {

    private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryImpl.class);

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected Class<Image> getCollectionClass() {
        return Image.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.IMAGES.getName();
    }

    @Override
    public List<Image> findByCriteria(ImageCriteria imageCriteria) {
        QueryHelper queryHelper = queryHelperFactory.getHelperByCriteria(imageCriteria.getClass());
        String query = queryHelper.getQuery(imageCriteria);
        Object[] parameters = queryHelper.getParameters(imageCriteria);
        String projection = queryHelper.getProjection(imageCriteria);
        String sort = queryHelper.getSort(imageCriteria);
        int skip = (imageCriteria.getSkip() != null) ? imageCriteria.getSkip().intValue() : 0;
        int limit = (imageCriteria.getLimit() != null) ? imageCriteria.getLimit().intValue() : 0;
        String hint = queryHelper.getHint(imageCriteria);
        if (logger.isDebugEnabled()) {
            String msg = String
                    .format("find images\n->queryHelper %s\n->query: %s\n->parameters: %s\n->projection: %s\n->sort: %s\n->skip: %d limit: %d\n->hint: %s",
                            queryHelper.getClass(), query, ArrayUtils.toString(parameters), projection, sort, skip,
                            limit, hint);
            logger.debug(msg);
        }
        MongoCollection coll = getCollection();
        Iterable<Image> iter = coll.find(query, parameters).projection(projection).sort(sort).skip(skip).limit(limit)
                .hint(hint).as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
    }

    @Override
    public long countByCriteria(ImageCriteria imageCriteria) {
        QueryHelper queryHelper = queryHelperFactory.getHelperByCriteria(imageCriteria.getClass());
        String query = queryHelper.getQuery(imageCriteria);
        Object[] parameters = queryHelper.getParameters(imageCriteria);
        if (logger.isDebugEnabled()) {
            String msg = String
                    .format("find images\n->queryHelper %s\n->query: %s\n->parameters: %s", queryHelper.getClass(), query, ArrayUtils.toString(parameters));
            logger.debug(msg);
        }
        MongoCollection coll = getCollection();
        return coll.count(query, parameters);
    }

    @Override
    public void updateNameAndText(String imageId, String name, String text) {
        String query = "{_id: #}";
        String updateQuery = "{$set: {name: #, text: #}}";
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, imageId).with(updateQuery, name, text);
        logger.debug("WriteResult for update image: {}", wr);
    }

    @Override
    public void incLike(String imageId) {
        String query = "{_id: #}";
        String updateQuery = "{$inc: {likes: 1}}";
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, imageId).with(updateQuery);
        logger.debug("WriteResult for inc image like: {}", wr);
    }

    @Override
    public void removeByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{target: #}", targetId);
        logger.debug("WriteResult for remove images by targetId: {}", wr);
    }
}
