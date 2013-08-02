package com.heaptrip.service.content;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.solr.SearchSolrContentResponse;
import com.heaptrip.domain.repository.solr.SolrContent;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.service.content.SolrContentService;
import com.heaptrip.domain.service.content.criteria.SearchContentResponse;
import com.heaptrip.domain.service.content.criteria.SolrContentCriteria;

@Service
public class SolrContentServiceImpl implements SolrContentService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private SolrContentRepository solrContentRepository;

	@Async
	@Override
	public void saveContent(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		try {
			// TODO konovalov: fix exception
			Content content = contentRepository.findOne(contentId);
			solrContentRepository.save(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	@Override
	public void removeContent(String contentId) {
		Assert.notNull(contentId, "contentId must not be null");
		try {
			// TODO konovalov: fix exception
			solrContentRepository.removeById(contentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SearchContentResponse findContentsBySolrContentCriteria(SolrContentCriteria criteria) {
		Assert.notNull(criteria, "criteria must not be null");
		Assert.notNull(criteria.getQuery(), "query text must not be null");
		Assert.notNull(criteria.getLocale(), "locale must not be null");

		SearchSolrContentResponse response = null;
		try {
			response = solrContentRepository.findBySolrContentCriteria(criteria);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		SearchContentResponse result = new SearchContentResponse();
		if (response == null) {
			result.setNumFound(0);
		} else {
			result.setNumFound(response.getNumFound());
			if (response.getContents() != null && !response.getContents().isEmpty()) {
				for (SolrContent solrContent : response.getContents()) {

				}
			}
		}

		return result;
	}
}
