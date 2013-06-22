package com.heaptrip.domain.repository.comment;

import java.util.List;

import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment> {

	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId);

	public void removeByTargetId(String targetId);
}
