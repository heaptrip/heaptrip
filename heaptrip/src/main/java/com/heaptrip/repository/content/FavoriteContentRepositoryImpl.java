package com.heaptrip.repository.content;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.repository.BaseRepositoryImpl;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FavoriteContentRepositoryImpl extends BaseRepositoryImpl implements FavoriteContentRepository {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteContentRepositoryImpl.class);

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }

    @Override
    public void addFavorite(String contentId, String accountId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll
                .update("{_id: #, 'favorites.ids': {$ne: #}}", contentId, accountId).with(
                        "{$push: {'favorites.ids': #}, $inc: {'favorites.count': 1}}", accountId);
        logger.debug("WriteResult for add favorite: {}", wr);
    }

    @Override
    public void removeFavorite(String contentId, String accountId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #, 'favorites.ids': #}", contentId, accountId).with(
                "{$pull: {'favorites.ids': #}, $inc: {'favorites.count': -1}}", accountId);
        logger.debug("WriteResult for remove favorite: {}", wr);
    }

    @Override
    public boolean exists(String contentId, String accountId) {
        MongoCollection coll = getCollection();
        Content content = coll.findOne("{_id: #, 'favorites.ids': #}", contentId, accountId).as(Content.class);
        return content != null;
    }

    @Override
    public List<String> findIdsByContentTypeAndAccountId(ContentEnum contentType, String accountId) {
        MongoCollection coll = getCollection();
        Iterator<Content> iter = coll.find("{'favorites.ids': #, _class: #}", accountId, contentType.getClazz())
                .projection("{_class: 1}").hint("{'favorites.ids': 1, _class: 1}").as(Content.class).iterator();

        List<String> result = new ArrayList<>();
        if (iter != null) {
            while (iter.hasNext()) {
                Content content = iter.next();
                result.add(content.getId());
            }
        }
        return result;
    }
}
