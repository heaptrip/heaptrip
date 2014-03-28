package com.heaptrip.repository.image;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.repository.image.ImageRepository;
import com.heaptrip.domain.service.image.criteria.ImageCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.mongodb.WriteResult;
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
        QueryHelper<ImageCriteria, Image> queryHelper = queryHelperFactory.getHelperByCriteria(imageCriteria);
        return queryHelper.findByCriteria(imageCriteria);
    }

    @Override
    public long countByCriteria(ImageCriteria imageCriteria) {
        QueryHelper<ImageCriteria, Image> queryHelper = queryHelperFactory.getHelperByCriteria(imageCriteria);
        return queryHelper.countByCriteria(imageCriteria);
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
