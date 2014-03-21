package com.heaptrip.domain.service.rating;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;

import java.util.concurrent.Future;

/**
 * Service to work with ratings
 */
public interface RatingService {

    /**
     * The method checks whether the user can rate content
     *
     * @param contentType content type
     * @param contentId   content ID
     * @param userId      user ID
     * @return can or can not
     */
    public boolean canSetRating(ContentEnum contentType, String contentId, String userId);

    /**
     * Add rating to content
     *
     * @param contentId content ID
     * @param userId    user ID
     * @param rating    value of rating
     * @return total rating for content
     */
    public Future<ContentRating> addContentRating(String contentId, String userId, double rating);

    /**
     * Add rating to account
     *
     * @param accountId account ID
     * @param userId    user ID
     * @param rating    value of rating
     * @return total rating for account
     */
    public Future<AccountRating> addAccountRating(String accountId, String userId, double rating);

    /**
     * Get default value of account rating. Need for used while account
     * creating.
     *
     * @return account rating
     */
    public AccountRating getDefaultAccountRating();

    /**
     * Get default value of content rating. Need for used while content creating
     * *
     *
     * @return content rating
     */
    public ContentRating getDefaultContentRating();

    /**
     * Convert rating value to starts.
     * <p/>
     * rating value from 0 to 0.19 	 - one star    <br/>
     * rating value from 0.2 to 0.39 - two stars   <br/>
     * rating value from 0.4 to 0.59 - three stars <br/>
     * rating value from 0.6 to 0.79 - four stars  <br/>
     * rating value from 0.8 to 1 	 - five stars  <br/>
     *
     * @param rating value of rating
     * @return stars
     */
    public double ratingToStars(double rating);

    /**
     * Convert starts to rating value
     * <p/>
     * one star 	- 0 rating value     <br/>
     * two stars 	- 0.25 rating value  <br/>
     * three stars 	- 0.5 rating value   <br/>
     * four stars 	- 0.75 rating value  <br/>
     * five stars	- 1 rating value     <br/>
     *
     * @param stars number of stars
     * @return rating value
     */
    public double starsToRating(double stars);
}
