package com.heaptrip.domain.repository.content;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

public interface ContentRepository extends CrudRepository<Content> {

	public void setDeleted(String tripId);

	public String getOwnerId(String contentId);

	public boolean isOwner(String contentId, String userId);

	public void setStatus(String contentId, ContentStatusEnum status, String[] allowed);

	public void incViews(String contentId, String userIdOrRemoteIp);

	public List<Content> findByIds(Collection<String> ids, Locale locale);

	public List<Content> findByFeedCriteria(FeedCriteria criteria);

	public List<Content> findByMyAccountCriteria(MyAccountCriteria criteria);

	public List<Content> findByForeignAccountCriteria(ForeignAccountCriteria criteria);

	public long getCountByFeedCriteria(FeedCriteria criteria);

	public long getCountByMyAccountCriteria(MyAccountCriteria criteria);

	public long getCountByForeignAccountCriteria(ForeignAccountCriteria criteria);

	public Date getDateCreated(String contentId);

	public ContentEnum getContentTypeByContentId(String contentId);

	public ContentRating getRating(String contentId);

	public void updateRating(String contentId, double ratingValue);
}
