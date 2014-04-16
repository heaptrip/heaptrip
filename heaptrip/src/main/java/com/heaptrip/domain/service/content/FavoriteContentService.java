package com.heaptrip.domain.service.content;

import java.util.concurrent.Future;

/**
 * Service to work with favorites contents
 */
public interface FavoriteContentService {

    /**
     * Add content to favorites
     *
     * @param contentId content id
     * @param accountId account id
     */
    public Future<Void> addFavorites(String contentId, String accountId);

    /**
     * Remove content from favorites
     *
     * @param contentId content id
     * @param accountId account id
     */
    public void removeFavorites(String contentId, String accountId);

    /**
     * The method checks whether the user can add content to favorites
     *
     * @param contentId content id
     * @param accountId account id
     * @return true or false
     */
    public boolean canAddFavorites(String contentId, String accountId);

}
