package com.heaptrip.repository.content.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Service
public class PostRepositoryImpl extends CrudRepositoryImpl<Post> implements PostRepository {

	private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);

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

		String updateQuery = null;
		List<Object> parameters = new ArrayList<>();

		updateQuery = String.format("{$set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.main': #,"
				+ "'summary.main': #, 'description.main': #, image: #}}");

		parameters.add(post.getCategories());
		parameters.add(post.getCategoryIds());
		parameters.add(post.getRegions());
		parameters.add(post.getRegionIds());
		parameters.add(post.getName().getValue());
		parameters.add(post.getSummary().getValue());
		parameters.add(post.getDescription().getValue());
		parameters.add(post.getImage());

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

}
