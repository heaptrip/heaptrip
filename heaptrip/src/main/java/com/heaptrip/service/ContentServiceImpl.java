package com.heaptrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.entity.FavoriteContent;
import com.heaptrip.domain.repository.ContentRepository;
import com.heaptrip.domain.repository.FavoriteContentRepository;
import com.heaptrip.domain.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	public void setContentStatus(String contentId, String ownerId, ContentStatusEnum status) {
		Assert.notNull(contentId, "tripId must not be null");
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(status, "status must not be null");
		String[] allowed = null;
		switch (status) {
		case PUBLISHED_ALL:
			allowed = new String[] { "0" };
			break;
		case PUBLISHED_FRIENDS:
			// TODO add owner freinds
			allowed = new String[] { "0" };
			break;
		default:
			allowed = new String[0];
			break;
		}
		contentRepository.setStatus(contentId, status, allowed);
	}

	@Override
	public void incContentViews(String contentId) {
		Assert.notNull(contentId, "tripId");
		contentRepository.incViews(contentId);
	}

	@Override
	public void addFavoriteContent(String contentId, ContentEnum contentType, String userId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(contentType, "contentType must not be null");
		Assert.notNull(userId, "userId must not be null");
		FavoriteContent fc = new FavoriteContent();
		fc.setContentId(contentId);
		fc.setType(contentType);
		fc.setUserId(userId);
		favoriteContentRepository.save(fc);
	}

	@Override
	public List<FavoriteContent> getFavoriteContents(ContentEnum contentType, String userId) {
		Assert.notNull(contentType, "contentType must not be null");
		Assert.notNull(userId, "userId must not be null");
		return favoriteContentRepository.findByTypeAndUserId(contentType, userId);
	}

	@Override
	public List<FavoriteContent> getFavoriteContents(String userId) {
		Assert.notNull(userId, "userId must not be null");
		return favoriteContentRepository.findByUserId(userId);
	}

	@Override
	public boolean isFavoriteContent(String contentId, String userId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(userId, "userId must not be null");
		FavoriteContent fc = favoriteContentRepository.findOneByContentIdAndUserId(contentId, userId);
		return (fc == null) ? false : true;
	}

	@Override
	public void removeFavoriteContent(String contentId, String userId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(userId, "userId must not be null");
		favoriteContentRepository.removeByContentIdAndUserId(contentId, userId);

	}

}
