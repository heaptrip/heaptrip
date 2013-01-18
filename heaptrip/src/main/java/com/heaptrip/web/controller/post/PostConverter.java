package com.heaptrip.web.controller.post;

import com.heaptrip.domain.entity.post.PostEntity;
import com.heaptrip.web.model.post.PostView;

class PostConverter {

	public static PostView postEntityToView(PostEntity entity) {
		PostView view = new PostView();
		view.setId(entity.getId());
		view.setName(entity.getName());
		view.setDateCreate(entity.getDateCreate());
		view.setDescription(entity.getDescription());
		return view;
	}

	public static PostEntity postViewToEntity(PostView view) {
		PostEntity entity = new PostEntity();
		entity.setId(view.getId());
		entity.setName(view.getName());
		entity.setDateCreate(view.getDateCreate());
		entity.setDescription(view.getDescription());
		return entity;
	}
}
