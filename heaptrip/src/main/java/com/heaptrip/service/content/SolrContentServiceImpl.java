package com.heaptrip.service.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.heaptrip.util.LanguageUtils;

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
				result.setContents(new ArrayList<Content>());
				// set list of id
				List<String> ids = new ArrayList<>();
				for (SolrContent solrContent : response.getContents()) {
					ids.add(solrContent.getId());
				}
				// read content
				Map<String, Content> contentMap = new HashMap<>();
				List<Content> contents = contentRepository.findByIds(ids, criteria.getLocale());
				if (contents != null && !contents.isEmpty()) {
					for (Content content : contents) {
						contentMap.put(content.getId(), content);
					}
				}
				// set result list
				for (SolrContent solrContent : response.getContents()) {
					Content content = contentMap.get(solrContent.getId());
					if (content != null) {
						if (StringUtils.isNotBlank(solrContent.getNameEn())) {
							content.getName().setValue(solrContent.getNameEn(), LanguageUtils.getEnglishLocale());
							content.getName().setMainLanguage(LanguageUtils.getEnglishLocale().getLanguage());
						}
						if (StringUtils.isNotBlank(solrContent.getNameRu())) {
							content.getName().setValue(solrContent.getNameRu(), LanguageUtils.getRussianLocale());
							content.getName().setMainLanguage(LanguageUtils.getRussianLocale().getLanguage());
						}
						if (StringUtils.isNotBlank(solrContent.getTextEn())) {
							content.getSummary().setValue(solrContent.getTextEn(), LanguageUtils.getEnglishLocale());
							content.getSummary().setMainLanguage(LanguageUtils.getEnglishLocale().getLanguage());
						}
						if (StringUtils.isNotBlank(solrContent.getTextRu())) {
							content.getSummary().setValue(solrContent.getTextRu(), LanguageUtils.getRussianLocale());
							content.getSummary().setMainLanguage(LanguageUtils.getRussianLocale().getLanguage());
						}
						result.getContents().add(content);
					}
				}
			}
		}

		return result;
	}
}
