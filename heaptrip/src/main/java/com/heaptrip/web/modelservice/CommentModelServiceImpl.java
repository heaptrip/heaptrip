package com.heaptrip.web.modelservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.comment.CommentAuthor;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.comment.CommentService;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.model.user.UserModel;

@Service
public class CommentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements CommentModelService {

	@Autowired
	private CommentService commentService;

	@Override
	public List<CommentModel> getComments(String targetId) {

		List<Comment> comments = commentService.getComments(targetId);

		Map<String, CommentModel> map = new HashMap<String, CommentModel>();
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
		commentModel.setAuthor(convertCommentAuthorToUserModel(comment.getAuthor()));
		return commentModel;
	}

	private UserModel convertCommentAuthorToUserModel(CommentAuthor author) {
		UserModel userModel = new UserModel();
		userModel.setImage(convertImage(author.getImage()));
		userModel.setName(author.getName());
		return userModel;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommentModel saveComment(CommentModel commentModel) {
		Comment comment = null;
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
