package com.heaptrip.domain.repository.content.post;

import java.io.InputStream;
import java.util.List;

import com.heaptrip.domain.entity.content.post.ImageEntity;
import com.heaptrip.domain.entity.content.post.PostEntity;

@Deprecated
public interface OldPostRepository {
	public static final String SERVICE_NAME = "postRepository";

	public void savePost(PostEntity post);

	public List<PostEntity> findAll();

	public PostEntity findById(String id);

	public void removeById(String id);

	public ImageEntity saveImage(InputStream is, String fileName);

	public InputStream getImage(String fileId);
}
