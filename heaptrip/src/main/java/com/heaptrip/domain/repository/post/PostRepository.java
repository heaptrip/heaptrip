package com.heaptrip.domain.repository.post;

import java.io.InputStream;
import java.util.List;

import com.heaptrip.domain.entity.post.PostEntity;

public interface PostRepository {
	public static final String SERVICE_NAME = "postRepository";

	public void savePost(PostEntity post);

	public List<PostEntity> findAll();

	public PostEntity findById(String id);

	public void removeById(String id);

	public String saveImage(InputStream is, String fileName);

	public InputStream getImage(String fileId);
}
