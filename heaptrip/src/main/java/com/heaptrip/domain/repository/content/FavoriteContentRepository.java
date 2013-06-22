package com.heaptrip.domain.repository.content;

import java.util.List;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.FavoriteContent;
import com.heaptrip.domain.repository.CrudRepository;

public interface FavoriteContentRepository extends CrudRepository<FavoriteContent> {

	public List<FavoriteContent> findByTypeAndUserId(ContentEnum contentType, String userId);

	public List<FavoriteContent> findByUserId(String userId);

	public FavoriteContent findOneByContentIdAndUserId(String contentId, String userId);

	public void removeByContentIdAndUserId(String contentId, String userId);

	public List<String> findContentIdsByTypeAndUserId(ContentEnum contentType, String userId);
}
