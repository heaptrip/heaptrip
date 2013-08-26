package com.heaptrip.domain.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Future;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.FavoriteContent;
import com.heaptrip.domain.entity.image.Image;
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
	 * @return number of views
	 */
	public Future<Long> incContentViews(String contentId, String userIdOrRemoteIp);

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
}
