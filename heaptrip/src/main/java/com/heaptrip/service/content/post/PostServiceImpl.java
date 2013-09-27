package com.heaptrip.service.content.post;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.service.content.ContentServiceImpl;

@Service
public class PostServiceImpl extends ContentServiceImpl implements PostService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private ContentSearchService contentSearchService;

	@Override
	public Post save(Post content, Locale locale) {
		Assert.notNull(content, "content must not be null");
		Assert.notNull(locale, "locale must not be null");
		Assert.notNull(content.getId(), "contentId must not be null");

		content.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
		content.setCreated(new Date());
		content.setDeleted(null);
		content.setRating(ContentRating.getDefaultValue());
		content.setComments(0L);

		// save to mongodb
		contentRepository.save(content);
		// save to solr
		contentSearchService.saveContent(content.getId());
		return content;
	}

	@Override
	public void remove(String tripId) {
		Assert.notNull(tripId, "tripId must not be null");
		super.remove(tripId);
		// remove from solr
		contentSearchService.removeContent(tripId);
	}

	@Override
	public void hardRemove(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		super.hardRemove(contentId);
		// remove from solr
		contentSearchService.removeContent(contentId);
	}

	@Override
	public void update(Post post) {
		// TODO Auto-generated method stub

	}

}
