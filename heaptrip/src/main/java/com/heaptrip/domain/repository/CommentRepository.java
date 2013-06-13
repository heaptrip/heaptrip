package com.heaptrip.domain.repository;

import java.util.List;

import com.heaptrip.domain.entity.Comment;

public interface CommentRepository {

	public Comment save(Comment comment);

	public Comment findById(String commentId);

	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId);

	public void removeByTargetId(String targetId);
}
