package com.heaptrip.domain.repository.content;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface ContentRepository extends CrudRepository<Content> {

    public void setDeleted(String tripId);

    public String getOwnerId(String contentId);

    public boolean isOwner(String contentId, String userId);

    public MultiLangText getName(String contentId);

    public ContentStatus getStatus(String contentId);

    public void setStatus(String contentId, ContentStatus status, String[] allowed);

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

    public void setRating(String contentId, ContentRating contentRating);

    public void updateRating(String contentId, double ratingValue);

    public void addAllowed(String ownerId, String userId);

    public void removeAllowed(String ownerId, String userId);

    public long getCountByOwnerIdAndAllowed(String ownerId, String allowedUserId);

    public String getMainLanguage(String contentId);

    public boolean haveActiveContent(String ownerId);
}
