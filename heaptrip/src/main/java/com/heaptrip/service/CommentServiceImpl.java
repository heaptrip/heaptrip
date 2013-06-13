package com.heaptrip.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.Comment;
import com.heaptrip.domain.entity.CommentAuthor;
import com.heaptrip.domain.repository.CommentRepository;
import com.heaptrip.domain.service.CommentService;
import com.heaptrip.util.SlugUtils;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment addComment(String targetId, String userId, String text) {
		Assert.notNull(targetId, "targetId must not be null");
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(text, "text must not be null");

		String slug = SlugUtils.generateSlug();
		String fullSlug = SlugUtils.getFullSlugBySlug(slug);

		Comment comment = new Comment();
		comment.setTarget(targetId);
		comment.setSlug(slug);
		comment.setFullSlug(fullSlug);
		comment.setCreated(new Date());
		comment.setText(text);

		CommentAuthor author = new CommentAuthor();
		author.setId(userId);
		// TODO set name and image for author
		comment.setAuthor(author);

		return commentRepository.save(comment);
	}

	@Override
	public Comment addChildComment(String parentId, String userId, String text) {
		Assert.notNull(parentId, "parentId must not be null");
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(text, "text must not be null");
		Comment rootComment = commentRepository.findOne(parentId);
		Assert.notNull(rootComment, "Error parentId");

		String slugPart = SlugUtils.generateSlug();
		String fullSlugPart = SlugUtils.getFullSlugBySlug(slugPart);
		String slug = rootComment.getSlug() + "/" + slugPart;
		String fullSlug = rootComment.getFullSlug() + "/" + fullSlugPart;

		Comment comment = new Comment();
		comment.setTarget(rootComment.getTarget());
		comment.setParent(parentId);
		comment.setSlug(slug);
		comment.setFullSlug(fullSlug);
		comment.setCreated(new Date());
		comment.setText(text);

		CommentAuthor author = new CommentAuthor();
		author.setId(userId);
		// TODO set name and image for author
		comment.setAuthor(author);

		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> getComments(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		return commentRepository.findByTargetIdOrderByFullSlugAsc(targetId);
	}

	@Override
	public void removeComments(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		commentRepository.removeByTargetId(targetId);
	}

}
