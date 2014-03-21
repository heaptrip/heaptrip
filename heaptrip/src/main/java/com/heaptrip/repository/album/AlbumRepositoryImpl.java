package com.heaptrip.repository.album;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.repository.album.AlbumRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumRepositoryImpl extends CrudRepositoryImpl<AlbumImage> implements AlbumRepository {

    private static final Logger logger = LoggerFactory.getLogger(AlbumRepositoryImpl.class);

    @Override
    protected String getCollectionName() {
        return AlbumImage.COLLECTION_NAME;
    }

    @Override
    protected Class<AlbumImage> getCollectionClass() {
        return AlbumImage.class;
    }

    @Override
    public List<AlbumImage> findByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        String fields = "{target: 1, 'refs.small': 1, ownerId: 1, uploaded: 1}";
        String hint = "{target: 1, uploaded: 1}";
        Iterable<AlbumImage> iter = coll.find("{target: #}", targetId).projection(fields).sort("{uploaded: 1}")
                .hint(hint).as(AlbumImage.class);
        return IteratorConverter.copyIterator(iter.iterator());
    }

    @Override
    public List<AlbumImage> findByTargetId(String targetId, int limit) {
        MongoCollection coll = getCollection();
        String fields = "{target: 1, 'refs.small': 1, ownerId: 1, uploaded: 1}";
        String hint = "{target: 1, uploaded: 1}";
        Iterable<AlbumImage> iter = coll.find("{target: #}", targetId).projection(fields).limit(limit)
                .sort("{uploaded: 1}").hint(hint).as(AlbumImage.class);
        return IteratorConverter.copyIterator(iter.iterator());
    }

    @Override
    public void updateAlbumImage(AlbumImage albumImage) {
        String query = "{_id: #}";
        String updateQuery = "{$set: {name: #, text: #}}";
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, albumImage.getId()).with(updateQuery, albumImage.getName(),
                albumImage.getText());
        logger.debug("WriteResult for update album image: {}", wr);
    }

    @Override
    public void incLike(String albumImageId) {
        String query = "{_id: #}";
        String updateQuery = "{$inc: {likes: 1}}";
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, albumImageId).with(updateQuery);
        logger.debug("WriteResult for inc album image like: {}", wr);
    }

    @Override
    public void removeByTargetId(String targetId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.remove("{target: #}", targetId);
        logger.debug("WriteResult for remove album images by targetId: {}", wr);
    }

}
