package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.FavoriteContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;

@Service
public class FavoriteContentServiceImpl implements FavoriteContentService {

    @Autowired
    private FavoriteContentRepository favoriteContentRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public void addFavorites(String contentId, String accountId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(accountId, "accountId must not be null");
        favoriteContentRepository.addFavorite(contentId, accountId);
    }

    @Override
    public List<Content> getFavoritesContents(String accountId, Locale locale) {
        Assert.notNull(accountId, "accountId must not be null");
        return favoriteContentRepository.findByAccountId(accountId, locale);
    }

    @Override
    public List<Content> getFavoritesContents(ContentEnum contentType, String accountId, Locale locale) {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(contentType, "contentType must not be null");
        return favoriteContentRepository.findByContentTypeAndAccountId(contentType, accountId, locale);
    }

    @Override
    public boolean canAddFavorites(String contentId, String accountId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(accountId, "accountId must not be null");
        String ownerId = contentRepository.getOwnerId(contentId);
        return !ownerId.equals(accountId) && !favoriteContentRepository.exists(contentId, accountId);
    }

    @Override
    public void removeFavorites(String contentId, String accountId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(accountId, "accountId must not be null");
        favoriteContentRepository.removeFavorite(contentId, accountId);
    }
}
