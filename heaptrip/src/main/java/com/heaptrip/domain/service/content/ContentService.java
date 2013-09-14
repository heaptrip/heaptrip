package com.heaptrip.domain.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

/**
 * 
 * Content service
 * 
 */
public interface ContentService {

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param feedCriteria
	 * @return contents list
	 */
	public List<Content> getContentsByFeedCriteria(FeedCriteria feedCriteria);

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param myAccountCriteria
	 * @return contents list
	 */
	public List<Content> getContentsByMyAccountCriteria(MyAccountCriteria myAccountCriteria);

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param foreignAccountCriteria
	 * @return contents list
	 */
	public List<Content> getContentsByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param feedCriteria
	 * @return number of contents
	 */
	public long getContentsCountByFeedCriteria(FeedCriteria feedCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param myAccountCriteria
	 * @return number of contents
	 */
	public long getContentsCountByMyAccountCriteria(MyAccountCriteria myAccountCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param foreignAccountCriteria
	 * @return number of contents
	 */
	public long getContentsCountByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria);

	/**
	 * Increase the content views: for registered users passed a user Id, for
	 * non-registered users passed ip address of the remote host
	 * 
	 * @param contentId
	 *            content id
	 * @param userIdOrRemoteIp
	 *            user id or remote host ip
	 * @return void
	 */
	public Future<Void> incViews(String contentId, String userIdOrRemoteIp);

	/**
	 * Set content status
	 * 
	 * @param contentId
	 * @param status
	 */
	public void setStatus(String contentId, ContentStatusEnum status);

	/**
	 * Add content to favorites
	 * 
	 * @param contentId
	 * @param accountId
	 */
	public void addFavorites(String contentId, String accountId);

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

	/**
	 * Checking that the content is a favorite
	 * 
	 * @param contentId
	 * @param accountId
	 * @return true or false
	 */
	public boolean isFavorites(String contentId, String accountId);

	/**
	 * Remove content from favorites
	 * 
	 * @param contentId
	 * @param accountId
	 */
	public void removeFavorites(String contentId, String accountId);

	/**
	 * Save the title image of content to GridFS
	 * 
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return image
	 * @throws IOException
	 */
	public Image saveTitleImage(String fileName, InputStream is) throws IOException;

	/**
	 * Get content rating by content id
	 * 
	 * @param contentId
	 *            content id
	 * @return content rating
	 */
	public ContentRating getContentRating(String contentId);

	/**
	 * Update content rating value by content id
	 * 
	 * @param contentId
	 *            content id
	 * @param ratingValue
	 *            new value for content rating
	 * 
	 */
	public void updateContentRatingValue(String contentId, double ratingValue);
}
