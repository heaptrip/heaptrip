package com.heaptrip.domain.repository.content;

import java.util.List;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.FeedCriteria;
import com.heaptrip.domain.service.content.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.MyAccountCriteria;

public interface ContentRepository extends CrudRepository<Content> {

	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed);

	public void incViews(String tripId);

	public List<Content> findByFeedCriteria(FeedCriteria criteria);

	public List<Content> findByMyAccountCriteria(MyAccountCriteria criteria);

	public List<Content> findByForeignAccountCriteria(ForeignAccountCriteria criteria);

	public long getCountByFeedCriteria(FeedCriteria criteria);

	public long getCountByMyAccountCriteria(MyAccountCriteria criteria);

	public long getCountByForeignAccountCriteria(ForeignAccountCriteria criteria);
}
