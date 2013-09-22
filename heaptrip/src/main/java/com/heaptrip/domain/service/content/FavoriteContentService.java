package com.heaptrip.domain.service.content;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;

public interface FavoriteContentService {

	/**
	 * Add content to favorites
	 * 
	 * @param contentId
	 * @param accountId
	 */
	public void addFavorites(String contentId, String accountId);

	/**
	 * Remove content from favorites
	 * 
	 * @param contentId
	 * @param accountId
	 */
	public void removeFavorites(String contentId, String accountId);

	/**
	 * Checking that the content is a favorite
	 * 
	 * @param contentId
	 * @param accountId
	 * @return true or false
	 */
	public boolean isFavorites(String contentId, String accountId);

	/**
	 * Get favorites contents by accountId
	 * 
	 * @param accountId
	 * @param locale
	 * @return content list
	 */
	public List<Content> getFavoritesContents(String accountId, Locale locale);

	/**
	 * Get favorites contents by contentType and accountId
	 * 
	 * @param contentType
	 * @param accountId
	 * @param locale
	 * @return content list
	 */
	public List<Content> getFavoritesContents(ContentEnum contentType, String accountId, Locale locale);

}
