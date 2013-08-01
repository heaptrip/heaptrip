
package com.heaptrip.service.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.service.content.SolrContentService;
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
		try {
			// TODO konovalov: fix exception
			solrContentRepository.removeById(contentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Content> findContentsBySolrContentCriteria(SolrContentCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
