package com.heaptrip.domain.service;

import java.util.List;

import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.FavoriteContent;

public interface ContentService {

	public void addFavoriteContent(String contentId, ContentEnum contentType, String userId);

	public List<FavoriteContent> getFavoriteContents(ContentEnum contentType, String userId);

	public List<FavoriteContent> getFavoriteContents(String userId);

	public boolean isFavoriteContent(String contentId, String userId);

	public void removeFavoriteContent(String contentId, String userId);
}
