package com.heaptrip.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.image.ImageService;

@Service(ContentService.SERVICE_NAME)
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ContentSearchService contentSearchService;

	@Override
	public boolean isOwner(String contentId, String userId) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(userId, "userId must not be null");
		return contentRepository.isOwner(contentId, userId);
	}

	@Override
	public void remove(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		contentRepository.setDeleted(contentId);
	}

	@Override
	public void hardRemove(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		contentRepository.remove(contentId);
	}

	@Override
	public ContentStatus getStatus(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		return contentRepository.getStatus(contentId);
	}

	@Override
	public void setStatus(String contentId, ContentStatus status) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(status, "status must not be null");
		Assert.notNull(status.getValue(), "status value must not be null");

		ContentStatus oldStatus = getStatus(contentId);
		if (oldStatus != null && oldStatus.getValue() != null && oldStatus.getValue().equals(status.getValue())) {
			// status is not changed
			return;
		}

		String[] allowed = null;

		switch (status.getValue()) {
		case PUBLISHED_ALL:
			allowed = new String[] { "0" };
			break;
		case PUBLISHED_FRIENDS:
			// TODO konovalov: add owner friends
			// String ownerId = contentRepository.getOwnerId(contentId);
			allowed = new String[] { "0" };
			break;
		default:
			allowed = new String[0];
			break;
		}

		// save status and allowed list
		contentRepository.setStatus(contentId, status, allowed);

		// update whole content (include allowed field) to Apache Solr
		contentSearchService.saveContent(contentId);
	}

	@Override
	public void addAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(userId, "userId must not be null");
		contentRepository.addAllowed(ownerId, userId);
	}

	@Override
	public void removeAllowed(String ownerId, String userId) {
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(userId, "userId must not be null");
		contentRepository.removeAllowed(ownerId, userId);
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
	public ContentRating getContentRating(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		return contentRepository.getRating(contentId);
	}

	@Override
	public void updateContentRatingValue(String contentId, double ratingValue) {
		Assert.notNull(contentId, "contentId must not be null");
		Assert.notNull(ratingValue, "ratingValue must not be null");
		contentRepository.updateRating(contentId, ratingValue);
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
