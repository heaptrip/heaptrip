package com.heaptrip.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.image.ImageService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ContentSearchService contentSearchService;

	@Override
	public List<Content> getContentsByFeedCriteria(FeedCriteria feedCriteria) {
		Assert.notNull(feedCriteria, "feedCriteria must not be null");
		return contentRepository.findByFeedCriteria(feedCriteria);
	}

	@Override
	// TODO konovalov add test
	public List<Content> getContentsByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
		Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
		Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
		return contentRepository.findByMyAccountCriteria(myAccountCriteria);
	}

	@Override
	// TODO konovalov add test
	public List<Content> getContentsByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
		Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getOwnerId()), "ownerId must not be null");
		return contentRepository.findByForeignAccountCriteria(foreignAccountCriteria);
	}

	@Override
	public long getContentsCountByFeedCriteria(FeedCriteria feedCriteria) {
		Assert.notNull(feedCriteria, "feedCriteria must not be null");
		return contentRepository.getCountByFeedCriteria(feedCriteria);
	}

	@Override
	// TODO konovalov add test
	public long getContentsCountByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
		Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
		Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
		return contentRepository.getCountByMyAccountCriteria(myAccountCriteria);
	}

	@Override
	// TODO konovalov add test
	public long getContentsCountByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
		Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
		Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
		Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
		Assert.isTrue(foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
		Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getOwnerId()), "ownerId must not be null");
		return contentRepository.getCountByForeignAccountCriteria(foreignAccountCriteria);
	}

	@Override
	public void setStatus(String contentId, ContentStatusEnum status) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(status, "status must not be null");
		String[] allowed = null;
		switch (status) {
		case PUBLISHED_ALL:
			allowed = new String[] { "0" };
			break;
		case PUBLISHED_FRIENDS:
			String ownerId = contentRepository.getOwnerId(contentId);
			// TODO konovalov: add owner friends
			allowed = new String[] { "0" };
			break;
		default:
			allowed = new String[0];
			break;
		}
		contentRepository.setStatus(contentId, status, allowed);
		// update whole content (include allowed field) to Apache Solr
		contentSearchService.saveContent(contentId);
	}

	@Async
	@Override
	public Future<Void> incViews(String contentId, String userIdOrRemoteIp) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(contentId, "userIdOrRemoteIp must not be null");
		contentRepository.incViews(contentId, userIdOrRemoteIp);
		return new AsyncResult<Void>(null);
	}

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
	public boolean isFavorites(String contentId, String accountId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(accountId, "accountId must not be null");
		return favoriteContentRepository.exists(contentId, accountId);
	}

	@Override
	public void removeFavorites(String contentId, String accountId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(accountId, "accountId must not be null");
		favoriteContentRepository.removeFavorite(contentId, accountId);
	}

	@Override
	public Image saveTitleImage(String fileName, InputStream is) throws IOException {
		Image image = new Image();
		String imageId = imageService.saveImage(fileName, ImageEnum.CONTENT_TITLE_IMAGE, is);
		image.setId(imageId);
		image.setName(fileName);
		image.setUploaded(new Date());
		return image;
	}
}
