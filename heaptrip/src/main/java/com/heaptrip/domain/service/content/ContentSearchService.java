package com.heaptrip.domain.service.content;

import com.heaptrip.domain.service.content.criteria.ContextSearchCriteria;
import com.heaptrip.domain.service.content.criteria.SearchContentResponse;

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
