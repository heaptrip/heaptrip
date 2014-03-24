package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.comment.CommentService;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.model.profile.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements CommentModelService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Override
    public List<CommentModel> getComments(String targetId) {

        List<Comment> comments = commentService.getComments(targetId);

        Map<String, CommentModel> map = new HashMap<>();
        map.put(null, new CommentModel());
        for (Comment comment : comments) {
            CommentModel commentModel = convertCommentToCommentModel(comment);
            map.put(commentModel.getId(), commentModel);
            map.get(comment.getParent()).addChildren(map.get(commentModel.getId()));
        }
        return map.get(null).getChildren();
    }

    private CommentModel convertCommentToCommentModel(Comment comment) {
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setText(comment.getText());
        commentModel.setCreated(convertDate(comment.getCreated()));
        commentModel.setTarget(comment.getTarget());
        commentModel.setAuthor(convertCommentAuthorToUserModel(comment.getAuthorId()));
        return commentModel;
    }

    private AccountModel convertCommentAuthorToUserModel(String authorId) {
        AccountModel userModel = new AccountModel();
        Account account = accountStoreService.findOne(authorId);
        if (account != null) {
            userModel.setImage(convertImage(account.getImage()));
            userModel.setName(account.getName());
        }
        return userModel;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public CommentModel saveComment(CommentModel commentModel) {
        Comment comment;
        Class clazz = null;
        if (commentModel.getTargetClass().equals("TRIP"))
            clazz = Trip.class;
        if (commentModel.getParent() == null)
            comment = commentService.addComment(clazz, commentModel.getTarget(), getCurrentUser().getId(),
                    commentModel.getText());
        else
            comment = commentService.addChildComment(clazz, commentModel.getTarget(), commentModel.getParent(),
                    getCurrentUser().getId(), commentModel.getText());
        return convertCommentToCommentModel(comment);
    }

}
