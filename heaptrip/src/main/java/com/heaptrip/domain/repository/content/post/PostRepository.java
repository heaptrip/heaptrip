package com.heaptrip.domain.repository.content.post;

import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post> {

	public void update(Post post);
	
}
