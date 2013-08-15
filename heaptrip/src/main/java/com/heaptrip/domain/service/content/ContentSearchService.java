package com.heaptrip.domain.service.content;

import org.springframework.scheduling.annotation.Async;

import com.heaptrip.domain.service.content.criteria.SearchContentResponse;
import com.heaptrip.domain.service.content.criteria.ContextSearchCriteria;

/**
 * 
 * Service for indexing and search documents in Apache Solr
 * 
 */
public interface ContentSearchService {

	/**
	 * Add content to Apache Solr by contentId
	 * 
	 * @param contentId
	 */
	@Async
	public void saveContent(String contentId);

	/**
	 * Remove content from Apache Solr by contentId
	 * 
	 * @param contentId
	 */
	public void removeContent(String contentId);

	/**
	 * Search contents by СontextSearchCriteria
	 * 
	 * @param ContextSearchCriteria
	 *            criteria
	 * 
	 * @return search content response
	 */
	public SearchContentResponse findContentsByСontextSearchCriteria(ContextSearchCriteria criteria);
}
