package com.heaptrip.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.FavoriteContent;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
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
	public void setContentStatus(String contentId, String ownerId, ContentStatusEnum status) {
		Assert.notNull(contentId, "contentId must not be null");
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
		Assert.notNull(contentId, "contentId must not be null");
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
