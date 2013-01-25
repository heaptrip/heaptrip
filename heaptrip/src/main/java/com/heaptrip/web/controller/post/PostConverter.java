package com.heaptrip.web.controller.post;

import java.util.ArrayList;
import java.util.List;

import com.heaptrip.domain.entity.post.ImageEntity;
import com.heaptrip.domain.entity.post.PostEntity;
import com.heaptrip.web.model.post.PostImage;
import com.heaptrip.web.model.post.PostView;

class PostConverter {

	public static PostView postEntityToView(PostEntity entity) {
		PostView view = new PostView();

		view.setId(entity.getId());
		view.setName(entity.getName());
		view.setDateCreate(entity.getDateCreate());
		view.setDescription(entity.getDescription());

		if (entity.getImages() != null && !entity.getImages().isEmpty()) {
			List<PostImage> images = new ArrayList<PostImage>();
			for (ImageEntity imageEntity : entity.getImages()) {
				PostImage imageView = new PostImage();
				imageView.setId(imageEntity.getId());
				imageView.setName(imageEntity.getName());
				imageView.setSize(imageEntity.getSize());
				images.add(imageView);
			}
			view.setImages(images);
		}

		return view;
	}

	public static PostEntity postViewToEntity(PostView view) {
		PostEntity entity = new PostEntity();
		entity.setId(view.getId());
		entity.setName(view.getName());
		entity.setDateCreate(view.getDateCreate());
		entity.setDescription(view.getDescription());

		if (view.getImages() != null && !view.getImages().isEmpty()) {
			List<ImageEntity> images = new ArrayList<ImageEntity>();
			for (PostImage imageView : view.getImages()) {
				ImageEntity imageEntity = new ImageEntity();
				imageEntity.setId(imageView.getId());
				imageEntity.setName(imageView.getName());
				imageEntity.setSize(imageView.getSize());
				images.add(imageEntity);
			}
			entity.setImages(images);
		}

		return entity;
	}
}
