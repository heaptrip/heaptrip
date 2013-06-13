package com.heaptrip.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.Comment;
import com.heaptrip.domain.service.CommentService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CommentServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TARGET_ID = "1";

	private static final String USER_ID = "1";

	@Autowired
	private CommentService commentService;

	@Test(enabled = true, priority = 0)
	public void addComment() throws IOException {
		// call
		Comment rootComment = commentService.addComment(TARGET_ID, USER_ID, "some root comment");
		// check
		Assert.assertNotNull(rootComment);
		Assert.assertNotNull(rootComment.getId());
		Assert.assertNull(rootComment.getParent());
		Assert.assertEquals(rootComment.getTarget(), TARGET_ID);
		Assert.assertEquals(rootComment.getAuthor().getId(), USER_ID);
		// call
		Comment childComment = commentService.addChildComment(rootComment.getId(), USER_ID, "some child comment");
		// check
		Assert.assertNotNull(childComment);
		Assert.assertNotNull(childComment.getId());
		Assert.assertNotNull(childComment.getParent());
		Assert.assertEquals(childComment.getParent(), rootComment.getId());
		Assert.assertEquals(childComment.getTarget(), TARGET_ID);
		Assert.assertEquals(childComment.getAuthor().getId(), USER_ID);
	}

	@Test(enabled = true, priority = 1)
	public void getComments() {
		// call
		List<Comment> comments = commentService.getComments(TARGET_ID);
		// check
		Assert.assertNotNull(comments);
		Assert.assertEquals(comments.size(), 2);
		Comment rootComment = comments.get(0);
		Comment childComment = comments.get(1);
		Assert.assertNull(rootComment.getParent());
		Assert.assertNotNull(childComment.getParent());
		Assert.assertEquals(childComment.getParent(), rootComment.getId());
	}

	@Test(enabled = true, priority = 2)
	public void removeComments() {
		// call
		commentService.removeComments(TARGET_ID);
		// check
		List<Comment> comments = commentService.getComments(TARGET_ID);
		Assert.assertTrue(comments == null || comments.size() == 0);
	}
}
