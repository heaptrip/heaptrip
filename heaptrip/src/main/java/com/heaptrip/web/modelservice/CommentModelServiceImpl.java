package com.heaptrip.web.modelservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.comment.CommentAuthor;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.comment.CommentService;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.model.user.UserModel;

@Service
public class CommentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements CommentModelService {

	@Autowired
	private CommentService commentService;

	// TODO:удрать после тестирования
	@Autowired
	private CategoryService categoryService;

	@Override
	public List<CommentModel> getComments(String targetId) {

		// TODO : удрать после тестирования
		// List<Comment> comments = commentService.getComments(targetId);
		List<Comment> comments = getTESTComments();

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

	@Deprecated
	// TODO:удрать после тестирования
	private List<Comment> getTESTComments() {
		List<Comment> comments = new ArrayList<>();
		List<Category> categories = categoryService.getCategories(new Locale("ru"));
		for (Category category : categories) {
			Comment comment = new Comment();
			comment.setId(category.getId());
			comment.setParent(category.getParent());
			comment.setText(category.getName().getValue(new Locale("ru")));
			comment.setCreated(new Date());
			CommentAuthor author = new CommentAuthor();
			author.setName(getCurrentUser() != null ? getCurrentUser().getName() : "Todo User");
			comment.setAuthor(author);
			comments.add(comment);
		}
		return comments;
	}

}
