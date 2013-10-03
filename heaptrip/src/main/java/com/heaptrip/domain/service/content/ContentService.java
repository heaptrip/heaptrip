package com.heaptrip.domain.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.ContentRating;

/**
 * 
 * Content service
 * 
 */
public interface ContentService {

	public String SERVICE_NAME = "contentService";

	/**
	 * Check that the user included in the list of content owners. You should
	 * use this method when verifying the right to a content editing.
	 * 
	 * @param contentId
	 *            content id
	 * @param userId
	 *            user id
	 * @return true or false
	 */
	public boolean isOwner(String contentId, String userId);

	/**
	 * Soft remove content
	 * 
	 * @param contentId
	 */
	public void remove(String contentId);

	/**
	 * Hard remove content. It is recommended to use the after tests to clear
	 * data
	 * 
	 * @param contentId
	 */
	public void hardRemove(String contentId);

	/**
	 * Set content status
	 * 
	 * @param contentId
	 * @param status
	 */
	public void setStatus(String contentId, ContentStatus status);

	/**
	 * Get content status
	 * 
	 * @param contentId
	 * @return status
	 */
	public ContentStatus getStatus(String contentId);

	/**
	 * Add a user to the list of allowed to view all contents this owner. Must
	 * be called this method when adding friend to owner
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void addAllowed(String ownerId, String userId);

	/**
	 * Remove the user from the list of allowed to view all contents this owner
	 * 
	 * @param ownerId
	 * @param userId
	 */
	public void removeAllowed(String ownerId, String userId);

	/**
	 * Increase the content views
	 * 
	 * @param contentId
	 *            content id
	 * @param remoteHostIp
	 *            remote host ip
	 * @return void
	 */
	public Future<Void> incViews(String contentId, String remoteHostIp);

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
