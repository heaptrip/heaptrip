package com.heaptrip.domain.repository;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.ContentStatusEnum;

public interface ContentRepository {

	public Content findById(String contentId);

	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed);

	public void incViews(String tripId);
}
