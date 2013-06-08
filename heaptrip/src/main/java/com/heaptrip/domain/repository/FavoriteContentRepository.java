package com.heaptrip.domain.repository;

import java.util.List;

import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.FavoriteContent;

public interface FavoriteContentRepository {

	public void save(FavoriteContent favoriteContent);

	public List<FavoriteContent> findByTypeAndUserId(ContentEnum contentType, String userId);

	public List<FavoriteContent> findByUserId(String userId);

	public FavoriteContent findOneByContentIdAndUserId(String contentId, String userId);

	public void removeByContentIdAndUserId(String contentId, String userId);

	public List<String> findContentIdsByTypeAndUserId(ContentEnum contentType, String userId);
}
