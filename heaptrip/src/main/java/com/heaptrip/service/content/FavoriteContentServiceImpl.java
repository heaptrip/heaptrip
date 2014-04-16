package com.heaptrip.service.content;

import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.FavoriteContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.Future;

@Service
public class FavoriteContentServiceImpl implements FavoriteContentService {

    @Autowired
    private FavoriteContentRepository favoriteContentRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Async
    @Override
    public Future<Void> addFavorites(String contentId, String accountId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(accountId, "accountId must not be null");
        favoriteContentRepository.addFavorite(contentId, accountId);
        return new AsyncResult<>(null);
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
