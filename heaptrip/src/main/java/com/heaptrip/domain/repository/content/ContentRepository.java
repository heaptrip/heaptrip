package com.heaptrip.domain.repository.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content> {

	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed);

	public void incViews(String tripId);
}
