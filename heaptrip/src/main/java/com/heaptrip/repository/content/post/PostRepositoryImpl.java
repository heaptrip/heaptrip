package com.heaptrip.repository.content.post;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

public class PostRepositoryImpl extends CrudRepositoryImpl<Post> implements PostRepository {

	@Override
	protected Class<Post> getCollectionClass() {
		return Post.class;
	}

	@Override
	protected String getCollectionName() {
		return CollectionEnum.CONTENTS.getName();
	}

}
