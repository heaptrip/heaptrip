package com.heaptrip.service.comment;

import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CommentServiceTest extends AbstractTestNGSpringContextTests {

    private static final String TARGET_ID = CommentServiceTest.class.getName();

    private static final String USER_ID = "1";

    @Autowired
    private CommentService commentService;

    @Autowired
    private TripRepository tripRepository;

    private Trip trip;

    @BeforeClass
    public void beforeTest() {
        // save trip
        trip = new Trip();
        trip.setId(TARGET_ID);
        tripRepository.save(trip);
        // remove old test comments
        commentService.removeComments(TARGET_ID);
    }

    @AfterClass
    public void afterTest() {
        // remove trip
        if (trip != null && trip.getId() != null) {
            tripRepository.remove(trip);
        }
        // remove test comments
        commentService.removeComments(TARGET_ID);
    }

    @Test(enabled = true, priority = 0)
    public void addCommentsWithCounter() {
        // call
        Comment rootComment = commentService.addComment(Trip.class, TARGET_ID, USER_ID, "some root comment");
        // check
        Assert.assertNotNull(rootComment);
        Assert.assertNotNull(rootComment.getId());
        Assert.assertNull(rootComment.getParent());
        Assert.assertEquals(rootComment.getTarget(), TARGET_ID);
        Assert.assertEquals(rootComment.getAuthorId(), USER_ID);
        // call
        Comment childComment = commentService.addChildComment(Trip.class, TARGET_ID, rootComment.getId(), USER_ID,
                "some child comment");
        // check
        Assert.assertNotNull(childComment);
        Assert.assertNotNull(childComment.getId());
        Assert.assertNotNull(childComment.getParent());
        Assert.assertEquals(childComment.getParent(), rootComment.getId());
        Assert.assertEquals(childComment.getTarget(), TARGET_ID);
        Assert.assertEquals(childComment.getAuthorId(), USER_ID);
        // check number of comments
        Trip content = tripRepository.findOne(TARGET_ID);
        Assert.assertNotNull(content);
        Assert.assertEquals(content.getComments(), 2L);
    }

    @Test(enabled = true, priority = 1)
    public void addComment() {
        Comment rootComment = commentService.addComment(TARGET_ID, USER_ID, "some root comment");
        // check
        Assert.assertNotNull(rootComment);
        Assert.assertNotNull(rootComment.getId());
        Assert.assertNull(rootComment.getParent());
        Assert.assertEquals(rootComment.getTarget(), TARGET_ID);
        Assert.assertEquals(rootComment.getAuthorId(), USER_ID);
        // call
        Comment childComment = commentService.addChildComment(rootComment.getId(), USER_ID, "some child comment");
        // check
        Assert.assertNotNull(childComment);
        Assert.assertNotNull(childComment.getId());
        Assert.assertNotNull(childComment.getParent());
        Assert.assertEquals(childComment.getParent(), rootComment.getId());
        Assert.assertEquals(childComment.getTarget(), TARGET_ID);
        Assert.assertEquals(childComment.getAuthorId(), USER_ID);
    }

    @Test(enabled = true, priority = 2)
    public void getComments() {
        // call
        List<Comment> comments = commentService.getComments(TARGET_ID);
        // check
        Assert.assertNotNull(comments);
        Assert.assertEquals(comments.size(), 4);
        // check first parent/child comments
        Comment rootComment = comments.get(0);
        Comment childComment = comments.get(1);
        Assert.assertNull(rootComment.getParent());
        Assert.assertNotNull(childComment.getParent());
        Assert.assertEquals(childComment.getParent(), rootComment.getId());
        // check second parent/child comments
        rootComment = comments.get(2);
        childComment = comments.get(3);
        Assert.assertNull(rootComment.getParent());
        Assert.assertNotNull(childComment.getParent());
        Assert.assertEquals(childComment.getParent(), rootComment.getId());
    }

    @Test(enabled = true, priority = 3)
    public void removeComments() {
        // call
        commentService.removeComments(TARGET_ID);
        // check
        List<Comment> comments = commentService.getComments(TARGET_ID);
        Assert.assertTrue(comments == null || comments.size() == 0);
    }
}
