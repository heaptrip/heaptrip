package com.heaptrip.domain.repository.content;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.repository.Repository;

import java.util.List;

public interface FavoriteContentRepository extends Repository {

    public void addFavorite(String contentId, String accountId);

    public void removeFavorite(String contentId, String accountId);

    public boolean exists(String contentId, String accountId);

    public List<String> findIdsByContentTypeAndAccountId(ContentEnum contentType, String accountId);
}
