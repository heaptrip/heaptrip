package com.heaptrip.domain.repository;

import java.util.List;

import com.heaptrip.domain.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment> {

	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId);

	public void removeByTargetId(String targetId);
}
