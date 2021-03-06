package com.heaptrip.repository.content.post;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class PostRepositoryImpl extends CrudRepositoryImpl<Post> implements PostRepository {

    private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected Class<Post> getCollectionClass() {
        return Post.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }

    @Override
    public void update(Post post) {
        String query = "{_id: #}";

        String updateQuery;
        List<Object> parameters = new ArrayList<>();

        updateQuery = "{$set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.mainText': #, 'summary.mainText': #, 'description.mainText': #}}";

        parameters.add(post.getCategories());
        parameters.add(post.getCategoryIds());
        parameters.add(post.getRegions());
        parameters.add(post.getRegionIds());
        parameters.add(post.getName().getValue());
        parameters.add(post.getSummary().getValue());
        parameters.add(post.getDescription().getValue());

        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "update post\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
                    post.getId(), updateQuery, ArrayUtils.toString(parameters));
            logger.debug(msg);
        }

        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, post.getId()).with(updateQuery, parameters.toArray());
        logger.debug("WriteResult for update post: {}", wr);
    }

    @Override
    public List<Post> findByIds(String[] ids, Locale locale) {
        MongoCollection coll = getCollection();
        QueryHelper<FeedCriteria, Content> queryHelper = queryHelperFactory.getHelperByCriteriaClass(FeedCriteria.class);
        FeedCriteria criteria = new FeedCriteria();
        criteria.setLocale(locale);
        Iterable<Post> data = coll.find("{_id: {$in: #}}", Arrays.asList(ids))
                .projection(queryHelper.getProjection(criteria)).as(getCollectionClass());
        return IteratorConverter.copyIterator(data.iterator());
    }

}
