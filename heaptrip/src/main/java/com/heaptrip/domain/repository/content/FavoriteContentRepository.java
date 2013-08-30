package com.heaptrip.domain.repository.content;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.repository.Repository;

public interface FavoriteContentRepository extends Repository {

	public void addFavorite(String contentId, String accountId);

	public void removeFavorite(String contentId, String accountId);

	public List<Content> findByAccountId(String accountId, Locale locale);

	public List<Content> findByContentTypeAndAccountId(ContentEnum contentType, String accountId, Locale locale);

	public boolean exists(String contentId, String accountId);

	public List<String> findIdsByContentTypeAndAccountId(ContentEnum contentType, String accountId);
}
