package com.heaptrip.domain.service.rating;

import java.util.concurrent.Future;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;

/**
 * 
 * Service to work with ratings
 * 
 */
public interface RatingService {

	/**
	 * The method checks whether the user can rate content
	 * 
	 * @param contentType
	 *            content type
	 * @param contentId
	 *            content ID
	 * @param userId
	 *            user ID
	 * @return can or can not
	 */
	public boolean canPutRating(ContentEnum contentType, String contentId, String userId);

	/**
	 * Add rating to content
	 * 
	 * @param contentId
	 *            content ID
	 * @param userId
	 *            user ID
	 * @param rating
	 *            value of rating
	 * @return total rating for content
	 */
	public Future<ContentRating> addContentRating(String contentId, String userId, double rating);

	/**
	 * Add rating to account
	 * 
	 * @param accountId
	 *            account ID
	 * @param userId
	 *            user ID
	 * @param rating
	 *            value of rating
	 * @return total rating for account
	 */
	public Future<AccountRating> addAccountRating(String accountId, String userId, double rating);
}
