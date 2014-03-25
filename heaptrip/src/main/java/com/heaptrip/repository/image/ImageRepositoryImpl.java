package com.heaptrip.repository.image;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.repository.image.ImageRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageRepositoryImpl extends CrudRepositoryImpl<Image> implements ImageRepository {

    private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryImpl.class);


    @Override
    protected Class<Image> getCollectionClass() {
        return Image.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.IMAGES.getName();
    }

    @Override
    public List<Image> findByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        String fields = "{name:1, text:1, target: 1, refs: 1, ownerId: 1, uploaded: 1, likes: 1}";
        String hint = "{target: 1, uploaded: 1}";
        Iterable<Image> iter = coll.find("{target: #}", targetId).projection(fields).sort("{uploaded: 1}")
                .hint(hint).as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
    }

    @Override
    public long getCountByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        return coll.count("{target: #}", targetId);
    }

    @Override
    public List<Image> findByTargetId(String targetId, int skip, int limit) {
        MongoCollection coll = getCollection();
        String fields = "{name:1, text:1, target: 1, refs: 1, ownerId: 1, uploaded: 1, likes: 1}";
        String hint = "{target: 1, uploaded: 1}";
        Iterable<Image> iter = coll.find("{target: #}", targetId).projection(fields).skip(skip).limit(limit)
                .sort("{uploaded: 1}").hint(hint).as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
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
