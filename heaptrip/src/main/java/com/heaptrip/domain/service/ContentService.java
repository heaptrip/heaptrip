package com.heaptrip.domain.service;

import java.util.List;

import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.FavoriteContent;

/**
 * 
 * Content service
 * 
 */
public interface ContentService {

	/**
	 * Increase the content views
	 * 
	 * @param contentId
	 */
	public void incContentViews(String contentId);

	/**
	 * Set content status
	 * 
	 * @param contentId
	 * @param ownerId
	 * @param status
	 */
	public void setContentStatus(String contentId, String ownerId, ContentStatusEnum status);

	/**
	 * Add content to favorites
	 * 
	 * @param contentId
	 * @param contentType
	 * @param userId
	 */
	public void addFavoriteContent(String contentId, ContentEnum contentType, String userId);

	/**
	 * Get favorite contents by contentType and userId
	 * 
	 * @param contentType
	 * @param userId
	 * @return
	 */
	public List<FavoriteContent> getFavoriteContents(ContentEnum contentType, String userId);

	/**
	 * Get favorite contents by userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<FavoriteContent> getFavoriteContents(String userId);

	/**
	 * Checking that the content is a favorite
	 * 
	 * @param contentId
	 * @param userId
	 * @return isFavorite
	 */
	public boolean isFavoriteContent(String contentId, String userId);

	/**
	 * Remove content from favorites
	 * 
	 * @param contentId
	 * @param userId
	 */
	public void removeFavoriteContent(String contentId, String userId);
}
