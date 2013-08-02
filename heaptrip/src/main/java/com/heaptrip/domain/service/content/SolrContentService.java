package com.heaptrip.domain.service.content;

import com.heaptrip.domain.service.content.criteria.SearchContentResponse;
import com.heaptrip.domain.service.content.criteria.SolrContentCriteria;

/**
 * 
 * Service for indexing and search documents in Apache Solr
 * 
 */
public interface SolrContentService {

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
	 * Search contents by SolrContentCriteria
	 * 
	 * @param SolrContentCriteria
	 *            criteria
	 * 
	 * @return search content response
	 */
	public SearchContentResponse findContentsBySolrContentCriteria(SolrContentCriteria criteria);
}
