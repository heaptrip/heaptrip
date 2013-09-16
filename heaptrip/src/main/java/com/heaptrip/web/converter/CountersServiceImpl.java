package com.heaptrip.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.system.RequestScopeService;

@Service
public class CountersServiceImpl implements CountersService {

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService scopeService;

	@Autowired
	private ContentService contentService;

	@Override
	public void incViews(String contentId) {
		contentService.incViews(contentId, scopeService.getCurrentRequestIP());
	}

}
