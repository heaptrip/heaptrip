package com.heaptrip.service.comment;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.Collectionable;
import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.comment.CommentAuthor;
import com.heaptrip.domain.entity.comment.Commentsable;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.SystemException;
import com.heaptrip.domain.repository.comment.CommentRepository;
import com.heaptrip.domain.service.comment.CommentService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.util.SlugUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ErrorService errorService;

    private <T extends BaseObject & Collectionable & Commentsable> void incCommentsNumber(Class<T> targetClass,
                                                                                          String targetId) {
        String collectionName;
        String numberFieldName;

        try {
            BaseObject collection = targetClass.newInstance();
            collectionName = ((Collectionable) collection).getCollectionName();
            numberFieldName = ((Commentsable) collection).getCommentsNumberFieldName();

            Assert.notNull(collectionName, "collection name for target must not be null");
            Assert.notNull(numberFieldName, "number field name for target must not be null");
        } catch (InstantiationException | IllegalAccessException e) {
            throw errorService.createException(SystemException.class, e, ErrorEnum.ERROR_ADD_COMMENT);
        }

        commentRepository.incCommentsNumber(collectionName, numberFieldName, targetId, 1);
    }

    @Override
    public <T extends BaseObject & Collectionable & Commentsable> Comment addComment(Class<T> targetClass,
                                                                                     String targetId, String userId, String text) {
        Assert.notNull(targetClass, "targetClass must not be null");
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(text, "text must not be null");
        Comment comment = addComment(targetId, userId, text);
        if (comment != null) {
            incCommentsNumber(targetClass, targetId);
        }
        return comment;
    }

    @Override
    public <T extends BaseObject & Collectionable & Commentsable> Comment addChildComment(Class<T> targetClass,
                                                                                          String targetId, String parentId, String userId, String text) {
        Assert.notNull(targetClass, "targetClass must not be null");
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(parentId, "parentId must not be null");
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(text, "text must not be null");
        Comment comment = addChildComment(parentId, userId, text);
        if (comment != null) {
            incCommentsNumber(targetClass, targetId);
        }
        return comment;
    }

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
