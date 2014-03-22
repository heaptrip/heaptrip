package com.heaptrip.domain.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;

import java.util.List;
import java.util.Locale;

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
    public void addFavorites(String contentId, String accountId);

    /**
     * Remove content from favorites
     *
     * @param contentId content id
     * @param accountId account id
     */
    public void removeFavorites(String contentId, String accountId);

    /**
     * Checking that the content is a favorite
     *
     * @param contentId content id
     * @param accountId account id
     * @return true or false
     */
    public boolean isFavorites(String contentId, String accountId);

    /**
     * Get favorites contents by accountId
     *
     * @param accountId account id
     * @param locale    requested locale
     * @return content list
     */
    public List<Content> getFavoritesContents(String accountId, Locale locale);

    /**
     * Get favorites contents by contentType and accountId
     *
     * @param contentType type of content
     * @param accountId   account id
     * @param locale      requested locale
     * @return content list
     */
    public List<Content> getFavoritesContents(ContentEnum contentType, String accountId, Locale locale);

}
